package com.plazoleta.msrestaurant.application.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TakeOrderResponse {
    private Long id;
    private Long clientId;
    private Long restaurantId;
    private Long chefId;
    private LocalDateTime date;
    private String status;
    private List<DishQuantityResponse> dishes;
}
