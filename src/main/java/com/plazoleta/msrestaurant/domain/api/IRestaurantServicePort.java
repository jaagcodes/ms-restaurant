package com.plazoleta.msrestaurant.domain.api;

import com.plazoleta.msrestaurant.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant);
    boolean isOwnerOfRestaurant(Long userId, Long restaurantId);
    List<Restaurant> getAllRestaurants(int page, int size);
}
