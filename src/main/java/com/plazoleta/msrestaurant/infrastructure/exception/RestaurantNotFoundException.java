package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class RestaurantNotFoundException extends ApplicationException{
    public RestaurantNotFoundException() {
        super("Restaurant not found", HttpStatus.NOT_FOUND);
    }
}
