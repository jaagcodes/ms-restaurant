package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.CreateRestaurantRequest;
import com.plazoleta.msrestaurant.application.handler.IRestaurantHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @PostMapping
    public ResponseEntity<Void> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        restaurantHandler.createRestaurant(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
