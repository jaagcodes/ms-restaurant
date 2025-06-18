package com.plazoleta.msrestaurant.application.mapper;

import com.plazoleta.msrestaurant.application.dto.CreateRestaurantRequest;
import com.plazoleta.msrestaurant.domain.model.Restaurant;

public class RestaurantRequestMapper {

    public Restaurant toRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        return new Restaurant(
                null,
                createRestaurantRequest.getName(),
                createRestaurantRequest.getAddress(),
                createRestaurantRequest.getPhone(),
                createRestaurantRequest.getLogoUrl(),
                createRestaurantRequest.getNit(),
                createRestaurantRequest.getOwnerId()
        );
    }
}
