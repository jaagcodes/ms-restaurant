package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class OwnerNotValidException extends ApplicationException{
    public OwnerNotValidException(){
        super("User does not have OWNER role", HttpStatus.UNAUTHORIZED);
    }
}
