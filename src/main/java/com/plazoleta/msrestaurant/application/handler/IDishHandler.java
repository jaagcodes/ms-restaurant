package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;

public interface IDishHandler {
    void createDish(CreateDishRequest request);
}
