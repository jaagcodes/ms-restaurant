package com.plazoleta.msrestaurant.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantResponse {
    private String name;
    private String urlLogo;
}
