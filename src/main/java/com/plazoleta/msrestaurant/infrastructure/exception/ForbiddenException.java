package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException{
    public ForbiddenException(){
        super("Forbidden exception consuming users service", HttpStatus.FORBIDDEN);
    }
}
