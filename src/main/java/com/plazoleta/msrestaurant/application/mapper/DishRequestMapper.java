package com.plazoleta.msrestaurant.application.mapper;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.domain.model.Dish;

public class DishRequestMapper {

    public Dish toDish(CreateDishRequest request) {
        return new Dish(
                null,
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getImageUrl(),
                request.getCategoryId(),
                request.getRestaurantId(),
                null,
                request.getOwnerId()
        );
    }
}
