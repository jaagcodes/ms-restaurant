package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateRestaurantRequest;
import com.plazoleta.msrestaurant.domain.model.Restaurant;

public interface IRestaurantHandler {
    void createRestaurant(CreateRestaurantRequest request);
}
