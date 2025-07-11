package com.plazoleta.msrestaurant.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNotAllowed extends ApplicationException{

    public EmployeeNotAllowed() {
        super("Employee not assigned to the order", HttpStatus.BAD_REQUEST);
    }
}
