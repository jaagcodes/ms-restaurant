package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class RestaurantAlreadyExistsException extends ApplicationException {
    public RestaurantAlreadyExistsException() {
        super("A restaurant with the same NIT already exists", HttpStatus.CONFLICT);
    }
}