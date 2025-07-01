package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.DishResponse;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishStatusRequest;
import org.springframework.data.domain.Page;

public interface IDishHandler {
    void createDish(CreateDishRequest request);
    void updateDish(Long id, UpdateDishRequest request);
    void updateDishStatus(Long dishId, UpdateDishStatusRequest request);
    Page<DishResponse> getDishesByRestaurant(Long restaurantId, Long categoryId, int page, int size);
}
