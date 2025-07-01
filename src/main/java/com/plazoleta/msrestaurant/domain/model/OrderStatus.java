package com.plazoleta.msrestaurant.domain.model;

public enum OrderStatus {
    PENDING,          // Pedido recién creado
    IN_PREPARATION,   // Aceptado y en proceso de preparación
    READY,            // Listo para ser reclamado
    DELIVERED,        // Ya fue entregado al cliente
    CANCELED
}
