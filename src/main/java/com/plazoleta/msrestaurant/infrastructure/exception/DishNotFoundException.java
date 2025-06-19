package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class DishNotFoundException extends ApplicationException{
    public DishNotFoundException() {
        super("Dish not found", HttpStatus.NOT_FOUND);
    }
}
