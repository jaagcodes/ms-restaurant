package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
