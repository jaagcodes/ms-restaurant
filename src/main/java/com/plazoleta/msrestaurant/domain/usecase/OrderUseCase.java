package com.plazoleta.msrestaurant.domain.usecase;

import com.plazoleta.msrestaurant.domain.api.IOrderServicePort;
import com.plazoleta.msrestaurant.domain.api.ISecurityServicePort;
import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import com.plazoleta.msrestaurant.domain.model.SendSms;
import com.plazoleta.msrestaurant.domain.model.User;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IOrderPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.ISmsClientPort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class OrderUseCase implements IOrderServicePort {

    private static final Logger log = LoggerFactory.getLogger(OrderUseCase.class);
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final ISecurityServicePort securityServicePort;
    private final IUserClientPort userClientPort;
    private final ISmsClientPort smsClientPort;

    public OrderUseCase(
            IOrderPersistencePort orderPersistencePort,
            IDishPersistencePort dishPersistencePort,
            ISecurityServicePort securityServicePort,
            IUserClientPort userClientPort,
            ISmsClientPort smsClientPort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.securityServicePort = securityServicePort;
        this.userClientPort = userClientPort;
        this.smsClientPort = smsClientPort;
    }


    public Order createOrder(Order order) {
        Long clientId = securityServicePort.getCurrentUserId();
        order.setClientId(clientId);
        if (orderPersistencePort.hasActiveOrder(order.getClientId())) {
            throw new ClientWithActiveOrder();
        }

        // Validar que todos los platos pertenezcan al mismo restaurante
        Long restaurantId = order.getRestaurantId();
        boolean inconsistentDishes = order.getDishes().stream()
                .anyMatch(d -> !dishPersistencePort.isDishFromRestaurant(d.getDishId(), restaurantId));

        log.info("🔄 [Order UseCase] inconsistent dishes: {}", inconsistentDishes);
        if (inconsistentDishes) {
            throw new InconsistentRestaurantDishes();
        }
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        log.info("🔄 [UseCase] saving order: {}", order.toString());
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Page<Order> getOrdersByStatus(OrderStatus status, Long restaurantId, int page, int size) {
        Long currentUserId = securityServicePort.getCurrentUserId();

        boolean isEmployeeOfRestaurant = userClientPort.isEmployeeOfRestaurant(currentUserId,restaurantId);
        if (!isEmployeeOfRestaurant) {
            throw new NotARestaurantEmployee();
        }

        return orderPersistencePort.findByStatusAndRestaurant(status, restaurantId, page, size);
    }

    @Override
    public Order takeOrder(Long orderId) {
        Long currentEmployeeId = securityServicePort.getCurrentUserId();

        Order order = orderPersistencePort.findById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(); // Excepción personalizada
        }
        log.info("🔄 [UseCase] takeOrder getting current order: {}", order.toString());

        if (!OrderStatus.PENDING.equals(order.getStatus())) {
            throw new InvalidOrderStatusException(OrderStatus.PENDING.toString(), OrderStatus.IN_PREPARATION.toString());
        }

        boolean isEmployeeOfRestaurant = userClientPort.isEmployeeOfRestaurant(currentEmployeeId, order.getRestaurantId());
        if (!isEmployeeOfRestaurant) {
            throw new NotARestaurantEmployee(); // Excepción personalizada
        }

        order.setChefId(currentEmployeeId);
        order.setStatus(OrderStatus.IN_PREPARATION);

        return orderPersistencePort.updateOrder(order); // Devolvemos la orden actualizada
    }

    @Override
    public Order markOrderReady(Long orderId) {
        Long currentEmployeeId = securityServicePort.getCurrentUserId();

        Order order = orderPersistencePort.findById(orderId);
        if(order == null) {
            throw new OrderNotFoundException();
        }

        log.info("[UseCase] validating IN_PREPARATION state order: {}", order.toString());
        if(!OrderStatus.IN_PREPARATION.equals(order.getStatus())) {
            throw new InvalidOrderStatusException(OrderStatus.IN_PREPARATION.toString(), OrderStatus.READY.toString());
        }

        if(!currentEmployeeId.equals(order.getChefId())) {
            throw new NotARestaurantEmployee();
        }

        String pin = generateSecurityPin();
        order.setSecurityPin(pin);
        order.setStatus(OrderStatus.READY);
        log.info("[UseCase] Updating Order: {}", order.toString());
        Order updatedOrder = orderPersistencePort.updateOrder(order);
        log.info("[UseCase] Updated and Persisted Order: {}", updatedOrder.toString());
        try{
            log.info("[UseCase] Getting user info: {}", order.getClientId());
            User client = userClientPort.getUserById(order.getClientId());
            log.info("[UseCase] User info: {}", client.toString());
            String phone = client.getPhoneNumber();
            if(phone != null && !phone.isBlank()) {
                String message = String.format("🍽️ Tu pedido #%d está listo para ser recogido. Tu PIN de seguridad es: %s", orderId, pin);
                smsClientPort.sendSms(new SendSms(phone, message));
            } else {
                log.warn("⚠️ Cliente {} no tiene un número de teléfono registrado", client.getId());
            }
        } catch(Exception e) {
            log.error("❌ Error al enviar el SMS al cliente del pedido {}", orderId, e);
        }
        return updatedOrder;
    }

    @Override
    public Order markOrderDelivered(Long orderId, String providedPin) {
        Order order = orderPersistencePort.findById(orderId);
        if(order == null) {
            throw new OrderNotFoundException();
        }
        log.info("[UseCase] Comparing current user ID: {} employee assigned to the order: {}", securityServicePort.getCurrentUserId(), order.getChefId());
        if(!Objects.equals(securityServicePort.getCurrentUserId(), order.getChefId())) {
            throw new NotARestaurantEmployee();
        }
        log.info("[UseCase] Comparing order securityPIN: {} with provided PIN: {}", order.getSecurityPin(), providedPin);
        if(!Objects.equals(order.getSecurityPin(), providedPin)) {
            throw new InvalidSecurityPINException();
        }
        log.info("[UseCase] Verifying if order status: {} is: {}", order.getStatus(), OrderStatus.READY);
        if(!order.getStatus().equals(OrderStatus.READY)) {
            throw new InvalidOrderStatusException(OrderStatus.READY.toString(), OrderStatus.DELIVERED.toString());
        }
        order.setStatus(OrderStatus.DELIVERED);
        log.info("[UseCase] Updating Order: {}", order.toString());
        Order updatedOrder = orderPersistencePort.updateOrder(order);
        log.info("[UseCase] Updated and Persisted Order: {}", updatedOrder.toString());
        return updatedOrder;
    }

    @Override
    public Order markOrderCanceled(Long orderId) {
        Order order = orderPersistencePort.findById(orderId);
        if(order == null) {
            throw new OrderNotFoundException();
        }
        log.info("[UseCase] order to cancel: {}", order.toString());
        Long requestUserId = securityServicePort.getCurrentUserId();
        log.info("[UseCase] current  request userId: {}", requestUserId);
        if(!Objects.equals(securityServicePort.getCurrentUserId(), order.getClientId())) {
            throw new ClientNotAllowedException();
        }
        if(!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new InvalidOrderStatusException(OrderStatus.PENDING.toString(), OrderStatus.CANCELED.toString());
        }
        order.setStatus(OrderStatus.CANCELED);
        Order updatedOrder = orderPersistencePort.updateOrder(order);
        log.info("[UseCase] Updating Order: {}", updatedOrder.toString());
        return updatedOrder;
    }

    private String generateSecurityPin() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }


}
