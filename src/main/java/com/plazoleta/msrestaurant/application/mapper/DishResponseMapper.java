package com.plazoleta.msrestaurant.application.mapper;

import com.plazoleta.msrestaurant.application.dto.DishResponse;
import com.plazoleta.msrestaurant.domain.model.Dish;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishResponseMapper {

    public DishResponse toResponse(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                dish.getPrice(),
                dish.getDescription(),
                dish.getImageUrl(),
                dish.getActive(),
                null // Si luego quieres inyectar el nombre de la categoría, lo puedes hacer aquí
        );
    }

    public List<DishResponse> toResponseList(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

