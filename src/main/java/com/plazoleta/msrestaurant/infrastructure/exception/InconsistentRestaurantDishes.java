package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class InconsistentRestaurantDishes extends ApplicationException{
    public InconsistentRestaurantDishes(){
        super("All dishes in de order must be from the same restaurant", HttpStatus.BAD_REQUEST);
    }
}
