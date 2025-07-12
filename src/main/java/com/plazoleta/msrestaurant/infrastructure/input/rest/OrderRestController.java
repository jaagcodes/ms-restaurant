package com.plazoleta.msrestaurant.infrastructure.input.rest;

import com.plazoleta.msrestaurant.application.dto.*;
import com.plazoleta.msrestaurant.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @Operation(
            summary = "Marcar pedido como listo",
            description = "Cambia el estado del pedido a READY y envía un SMS con un PIN de seguridad al cliente que realizó el pedido.",
            tags = {"Pedidos"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content(schema = @Schema(example = "{ \"message\": \"Order not found\" }"))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "El empleado no tiene permisos para modificar este pedido",
                    content = @Content(schema = @Schema(example = "{ \"message\": \"User is not allowed to modify this order\" }"))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El pedido no se encuentra en estado válido para esta operación",
                    content = @Content(schema = @Schema(example = "{ \"message\": \"Invalid order status\" }"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error inesperado en el servidor",
                    content = @Content(schema = @Schema(example = "{ \"message\": \"Unexpected error\" }"))
            )
    })
    @PatchMapping("/{orderId}/ready")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<OrderResponse> markOrderAsReady(
            @Parameter(description = "ID del pedido que se desea marcar como listo", example = "123")
            @PathVariable Long orderId
    ) {
        log.info("✅ [REST] mark order as READY: {}", orderId);
        OrderResponse response = orderHandler.markOrderReady(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/deliver")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<OrderResponse> markAsDelivered(
            @PathVariable Long orderId,
            @RequestBody OrderDeliveredRequest orderDeliveredRequest
    ){
        log.info(" [REST] mark order as DELIVERED orderId: {}, PIN: {}", orderId, orderDeliveredRequest.getProvidedPin());
        OrderResponse response = orderHandler.markOrderDelivered(orderId, orderDeliveredRequest.getProvidedPin());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<OrderResponse> markAsCancel(
            @PathVariable Long orderId
    ){
        log.info(" [REST] mark order as CANCELED orderId: {}", orderId);
        OrderResponse response = orderHandler.markOrderCanceled(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
