package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ClientNotAllowedException extends ApplicationException{
    public ClientNotAllowedException(){
        super("The order doesn´t belong to the client trying to cancel it", HttpStatus.BAD_REQUEST);
    }
}
