package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishStatusRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishRestController {

    private static final Logger log = LoggerFactory.getLogger(DishRestController.class);
    private final IDishHandler dishHandler;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
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
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> updateDish(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRequest request) {
        dishHandler.updateDish(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> whoami() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(
                Map.of(
                        "principal", auth.getPrincipal(),
                        "authorities", auth.getAuthorities()
                )
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishStatusRequest request
    ){
        dishHandler.updateDishStatus(id, request);
        return ResponseEntity.noContent().build();
    }

}
