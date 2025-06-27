package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishStatusRequest;

public interface IDishHandler {
    void createDish(CreateDishRequest request);
    void updateDish(Long id, UpdateDishRequest request);
    void updateDishStatus(Long dishId, UpdateDishStatusRequest request);
}
