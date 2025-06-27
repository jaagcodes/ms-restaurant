package com.plazoleta.msrestaurant.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishStatusRequest {
    @NotNull(message = "The dish state is required")
    private Boolean active;
}
