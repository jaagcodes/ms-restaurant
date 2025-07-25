package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.CreateRestaurantRequest;
import com.plazoleta.msrestaurant.application.dto.RestaurantResponse;
import com.plazoleta.msrestaurant.application.handler.IRestaurantHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        restaurantHandler.createRestaurant(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<RestaurantResponse> restaurants = restaurantHandler.getAllRestaurants(page, size);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{restaurantId}/validate-owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Boolean> isOwnerOfRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam Long userId
    ){
        boolean isOwner = restaurantHandler.isOwnerOfRestaurant(userId, restaurantId);
        return ResponseEntity.ok(isOwner);
    }

}
