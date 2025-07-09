package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class InvalidOrderStatusException extends ApplicationException{
    public InvalidOrderStatusException() {
        super("To change order status to IN_PREPARATION current status must be PENDING", HttpStatus.BAD_REQUEST);
    }
}
