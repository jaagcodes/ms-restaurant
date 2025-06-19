package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;

public interface IDishHandler {
    void createDish(CreateDishRequest request);
    void updateDish(Long id, UpdateDishRequest request);
}
