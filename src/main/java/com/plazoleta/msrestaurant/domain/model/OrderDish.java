package com.plazoleta.msrestaurant.domain.model;

public class OrderDish {
    private Long dishId;
    private Integer quantity;

    public OrderDish(Long dishId, Integer quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDish{" +
                "dishId=" + dishId +
                ", quantity=" + quantity +
                '}';
    }
}
