package com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper;

import com.plazoleta.msrestaurant.domain.model.Restaurant;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {

    public RestaurantEntity toEntity(Restaurant restaurant) {
        return RestaurantEntity.builder()
                .name(restaurant.getName())
                .nit(restaurant.getNit())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .logoUrl(restaurant.getLogoUrl())
                .ownerId(restaurant.getOwnerId())
                .build();
    }

    public Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getNit(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getLogoUrl(),
                entity.getOwnerId()
        );
    }
}
