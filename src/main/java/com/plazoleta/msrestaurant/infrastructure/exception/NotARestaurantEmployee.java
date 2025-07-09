package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class NotARestaurantEmployee extends ApplicationException{
    public NotARestaurantEmployee() {
        super("Not a restaurant employee", HttpStatus.UNAUTHORIZED);
    }
}
