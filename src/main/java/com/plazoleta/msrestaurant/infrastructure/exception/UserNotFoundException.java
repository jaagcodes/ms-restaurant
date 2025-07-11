package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException{
    public UserNotFoundException(){
        super("User not found error", HttpStatus.NOT_FOUND);
    }
}
