package com.plazoleta.msrestaurant.application.dto;

public class DishQuantityResponse {

    private Long dishId;
    private Integer quantity;

    public DishQuantityResponse(Long dishId, Integer quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public Long getDishId() {
        return dishId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}