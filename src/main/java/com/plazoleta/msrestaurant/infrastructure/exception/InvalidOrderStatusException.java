package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class InvalidOrderStatusException extends ApplicationException{
    public InvalidOrderStatusException(String currentStatus, String newStatus) {
        super("To change order status to " + newStatus + " current status must be " + currentStatus, HttpStatus.BAD_REQUEST);
    }
}
