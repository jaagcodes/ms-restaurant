package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ClientWithActiveOrder extends ApplicationException {
    public ClientWithActiveOrder() {
        super("Client has an active order", HttpStatus.BAD_REQUEST);
    }
}
