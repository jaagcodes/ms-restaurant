package com.plazoleta.msrestaurant.domain.usecase;

import com.plazoleta.msrestaurant.domain.api.IOrderServicePort;
import com.plazoleta.msrestaurant.domain.api.ISecurityServicePort;
import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IOrderPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class OrderUseCase implements IOrderServicePort {

    private static final Logger log = LoggerFactory.getLogger(OrderUseCase.class);
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final ISecurityServicePort securityServicePort;
    private final IUserClientPort userClientPort;

    public OrderUseCase(
            IOrderPersistencePort orderPersistencePort,
            IDishPersistencePort dishPersistencePort,
            ISecurityServicePort securityServicePort,
            IUserClientPort userClientPort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.securityServicePort = securityServicePort;
        this.userClientPort = userClientPort;
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
            throw new InvalidOrderStatusException();
        }

        boolean isEmployeeOfRestaurant = userClientPort.isEmployeeOfRestaurant(currentEmployeeId, order.getRestaurantId());
        if (!isEmployeeOfRestaurant) {
            throw new NotARestaurantEmployee(); // Excepción personalizada
        }

        order.setChefId(currentEmployeeId);
        order.setStatus(OrderStatus.IN_PREPARATION);

        return orderPersistencePort.updateOrder(order); // Devolvemos la orden actualizada
    }


}
