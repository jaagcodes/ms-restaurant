package com.plazoleta.msrestaurant.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "Restaurant id is required")
    private Long restaurantId;

    @NotNull(message = "Must include at least one dish")
    private List<OrderDishRequest> dishes;

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                ", restaurantId=" + restaurantId +
                ", dishes=" + dishes +
                '}';
    }
}
