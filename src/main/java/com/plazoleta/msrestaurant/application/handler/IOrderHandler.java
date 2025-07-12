package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.dto.PaginatedOrderResponse;
import com.plazoleta.msrestaurant.application.dto.TakeOrderResponse;

public interface IOrderHandler {
    OrderResponse createOrder(CreateOrderRequest request);
    PaginatedOrderResponse getOrdersByStatus(String status, Long restaurantId, int page, int size);
    TakeOrderResponse takeOrder(Long orderId);
    OrderResponse markOrderReady(Long orderId);
    OrderResponse markOrderDelivered(Long orderId, String providedPIN);
    OrderResponse markOrderCanceled(Long orderId);
}
