package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends ApplicationException{
    public OrderNotFoundException() {
        super("Order not found", HttpStatus.NOT_FOUND);
    }
}
