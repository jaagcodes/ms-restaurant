package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class OwnerValidationException extends ApplicationException{
    public OwnerValidationException(){
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
