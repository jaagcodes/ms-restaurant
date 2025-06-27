package com.plazoleta.msrestaurant.domain.api;

import com.plazoleta.msrestaurant.domain.model.Dish;

public interface IDishServicePort {
    void createDish(Dish dish);
    void updateDish(Dish dish);
    void updateDishStatus(Long dishId, Boolean isActive);
}
