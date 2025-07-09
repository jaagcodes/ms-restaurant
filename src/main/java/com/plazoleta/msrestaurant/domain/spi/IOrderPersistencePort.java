package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import org.springframework.data.domain.Page;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    boolean hasActiveOrder(Long clientId);
    Page<Order> findByStatusAndRestaurant(OrderStatus status, Long restaurantId, int page, int size);
    Order findById(Long orderId);
    Order updateOrder(Order order);
}
