package com.plazoleta.msrestaurant.domain.api;

import com.plazoleta.msrestaurant.domain.model.Dish;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDishServicePort {
    void createDish(Dish dish);
    void updateDish(Dish dish);
    void updateDishStatus(Long dishId, Boolean isActive);
    Page<Dish> getDishesByRestaurant(Long restaurantId, Long categoryId, int page, int size);


}
