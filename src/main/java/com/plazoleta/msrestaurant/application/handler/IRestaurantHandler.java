package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateRestaurantRequest;
import com.plazoleta.msrestaurant.application.dto.RestaurantResponse;
import com.plazoleta.msrestaurant.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantHandler {
    void createRestaurant(CreateRestaurantRequest request);
    boolean isOwnerOfRestaurant(Long userId, Long restaurantId);
    List<RestaurantResponse> getAllRestaurants(int page, int size);
}
