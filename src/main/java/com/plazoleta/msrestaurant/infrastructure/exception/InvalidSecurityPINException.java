package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class InvalidSecurityPINException extends ApplicationException{

    public InvalidSecurityPINException(){
        super("Invalid Security PIN provided", HttpStatus.BAD_REQUEST);
    }
}
