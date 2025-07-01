package com.plazoleta.msrestaurant.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishRequest {

    @NotNull(message = "El ID del plato es obligatorio")
    private Long dishId;

    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;

    @Override
    public String toString() {
        return "OrderDishRequest{" +
                "dishId=" + dishId +
                ", quantity=" + quantity +
                '}';
    }
}
