package com.plazoleta.msrestaurant.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DishResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String imageUrl;
    private Boolean active;
    private String categoryName; // Opcional si deseas mostrar el nombre
}