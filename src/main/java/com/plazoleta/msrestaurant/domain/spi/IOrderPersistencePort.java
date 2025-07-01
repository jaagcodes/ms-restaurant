package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.Order;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    boolean hasActiveOrder(Long clientId);
}
