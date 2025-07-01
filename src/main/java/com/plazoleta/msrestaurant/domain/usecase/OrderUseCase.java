package com.plazoleta.msrestaurant.domain.usecase;

import com.plazoleta.msrestaurant.domain.api.IOrderServicePort;
import com.plazoleta.msrestaurant.domain.api.ISecurityServicePort;
import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IOrderPersistencePort;
import com.plazoleta.msrestaurant.infrastructure.exception.ClientWithActiveOrder;
import com.plazoleta.msrestaurant.infrastructure.exception.InconsistentRestaurantDishes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class OrderUseCase implements IOrderServicePort {

    private static final Logger log = LoggerFactory.getLogger(OrderUseCase.class);
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final ISecurityServicePort securityServicePort;

    public OrderUseCase(
            IOrderPersistencePort orderPersistencePort,
            IDishPersistencePort dishPersistencePort,
            ISecurityServicePort securityServicePort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.securityServicePort = securityServicePort;
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
}
