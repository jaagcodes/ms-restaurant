package com.plazoleta.msrestaurant.domain.api;

import com.plazoleta.msrestaurant.domain.model.Order;

public interface IOrderServicePort {
    Order createOrder(Order order);
}
