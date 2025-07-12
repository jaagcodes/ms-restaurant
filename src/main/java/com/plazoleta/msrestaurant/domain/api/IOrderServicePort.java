package com.plazoleta.msrestaurant.domain.api;

import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    Order createOrder(Order order);
    Page<Order> getOrdersByStatus(OrderStatus status, Long restaurantId, int page, int size);
    Order takeOrder(Long orderId);
    Order markOrderReady(Long orderId);
    Order markOrderDelivered(Long orderId, String providedPin);
    Order markOrderCanceled(Long orderId);
}
