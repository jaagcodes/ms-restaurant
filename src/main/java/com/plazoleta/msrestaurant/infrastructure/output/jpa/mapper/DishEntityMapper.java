package com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper;

import com.plazoleta.msrestaurant.domain.model.Dish;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.CategoryEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.DishEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class DishEntityMapper {

    public DishEntity toEntity(Dish dish) {
        return DishEntity.builder()
                .name(dish.getName())
                .price(dish.getPrice())
                .description(dish.getDescription())
                .urlImage(dish.getImageUrl())
                .category(CategoryEntity.builder().id(dish.getCategoryId()).build())
                .restaurant(RestaurantEntity.builder().id(dish.getRestaurantId()).build())
                .active(dish.getActive())
                .build();
    }

    public Dish toDomain(DishEntity entity) {
        return new Dish(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getUrlImage(),
                entity.getCategory().getId(),
                entity.getRestaurant().getId(),
                entity.getActive()
        );
    }
}
