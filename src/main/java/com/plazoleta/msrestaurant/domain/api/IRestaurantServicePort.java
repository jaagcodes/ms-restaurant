package com.plazoleta.msrestaurant.domain.api;

import com.plazoleta.msrestaurant.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant);
}
