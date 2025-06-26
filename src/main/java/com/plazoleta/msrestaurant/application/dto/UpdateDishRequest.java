package com.plazoleta.msrestaurant.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateDishRequest {


    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Integer price;

    @NotBlank(message = "Description is required")
    private String description;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateDishRequest{" +
                "price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
