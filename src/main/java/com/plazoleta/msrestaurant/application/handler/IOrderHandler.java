package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;

public interface IOrderHandler {
    OrderResponse createOrder(CreateOrderRequest request);
}
