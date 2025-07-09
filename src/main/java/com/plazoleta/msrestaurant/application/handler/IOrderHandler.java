package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.dto.PaginatedOrderResponse;
import com.plazoleta.msrestaurant.application.dto.TakeOrderResponse;
import org.springframework.data.domain.Page;

public interface IOrderHandler {
    OrderResponse createOrder(CreateOrderRequest request);
    PaginatedOrderResponse getOrdersByStatus(String status, Long restaurantId, int page, int size);
    TakeOrderResponse takeOrder(Long orderId);
}
