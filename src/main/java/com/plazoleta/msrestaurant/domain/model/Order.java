package com.plazoleta.msrestaurant.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long clientId;
    private Long restaurantId;
    private Long chefId;
    private List<OrderDish> dishes;
    private LocalDateTime date;
    private OrderStatus status;
    private String securityPin;

    public Order(Long id, Long clientId, Long restaurantId, List<OrderDish> dishes, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.dishes = dishes;
        this.date = date;
        this.status = status;
    }

    public Order(Long id, Long clientId, Long restaurantId, List<OrderDish> dishes, LocalDateTime date, OrderStatus status, Long chefId) {
        this.id = id;
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.dishes = dishes;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
    }

    public Order(Long id, Long clientId, Long restaurantId, List<OrderDish> dishes, LocalDateTime date, OrderStatus status, Long chefId, String securityPin) {
        this.id = id;
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.dishes = dishes;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
        this.securityPin = securityPin;
    }

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", restaurantId=" + restaurantId +
                ", chefId=" + chefId +
                ", dishes=" + dishes +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
