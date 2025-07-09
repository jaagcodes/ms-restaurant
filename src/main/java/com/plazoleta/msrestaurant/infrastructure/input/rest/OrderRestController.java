package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.dto.PaginatedOrderResponse;
import com.plazoleta.msrestaurant.application.dto.TakeOrderResponse;
import com.plazoleta.msrestaurant.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private static final Logger log = LoggerFactory.getLogger(OrderRestController.class);
    private final IOrderHandler orderHandler;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Crear un nuevo pedido para el cliente autenticado")
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")

    public ResponseEntity<OrderResponse> createOrder( @RequestBody @Valid CreateOrderRequest request) {
        log.info("🔄 [POST /orders] Creating Order: {}", request.toString());
        OrderResponse response = orderHandler.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PaginatedOrderResponse> getOrdersByStatus(
            @RequestParam String status,
            @RequestParam Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginatedOrderResponse response = orderHandler.getOrdersByStatus(status, restaurantId, page, size);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Take an order", description = "Allows an employee to take a PENDING order and assign themselves as the chef. The order status will be updated to IN_PREPARATION.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order taken successfully",
                    content = @Content(schema = @Schema(implementation = TakeOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order status or user not authorized"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("/{orderId}/take")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<TakeOrderResponse> takeOrder(@PathVariable Long orderId) {
        TakeOrderResponse response = orderHandler.takeOrder(orderId);
        return ResponseEntity.ok(response);
    }


}
