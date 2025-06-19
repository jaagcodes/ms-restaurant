package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;
import com.plazoleta.msrestaurant.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishRestController {

    private static final Logger log = LoggerFactory.getLogger(DishRestController.class);
    private final IDishHandler dishHandler;

    @PostMapping
    @Operation(summary = "Create a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Not allowed"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Void> createDish(@Valid @RequestBody CreateDishRequest request) {
        log.info("🔄 [POST /dishes] Creating dish: {} for restaurantId: {}", request.getName(), request.getRestaurantId());
        dishHandler.createDish(request);
        log.info("✅ Dish '{}' successfully created", request.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDish(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRequest request) {
        dishHandler.updateDish(id, request);
        return ResponseEntity.noContent().build();
    }
}
