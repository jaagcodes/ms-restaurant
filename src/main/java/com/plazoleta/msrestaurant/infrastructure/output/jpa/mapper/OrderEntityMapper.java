package com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper;

import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderDish;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderDishEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderDishId;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEntityMapper {

    public OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .clientId(order.getClientId())
                .restaurantId(order.getRestaurantId())
                .status(order.getStatus())
                .chefId(order.getChefId())
                .date(order.getDate())
                .build();

        // Mapear los platos del pedido
        List<OrderDishEntity> dishEntities = order.getDishes() != null
                ? order.getDishes().stream()
                .map(d -> OrderDishEntity.builder()
                        .id(new OrderDishId(null, d.getDishId()))
                        .order(orderEntity)
                        .quantity(d.getQuantity())
                        .build())
                .collect(Collectors.toList())
                : List.of();

        orderEntity.setDishes(dishEntities);
        return orderEntity;
    }

    public OrderEntity toEntityWithoutDishes(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .clientId(order.getClientId())
                .restaurantId(order.getRestaurantId())
                .status(order.getStatus())
                .chefId(order.getChefId())
                .date(order.getDate())
                .build();

        if(order.getId() != null) {
            orderEntity.setId(order.getId());
        }

        return orderEntity;
    }

    public Order toModel(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getClientId(),
                entity.getRestaurantId(),
                entity.getDishes().stream()
                        .map(d -> new OrderDish(d.getId().getDishId(), d.getQuantity()))
                        .collect(Collectors.toList()),
                entity.getDate(),
                entity.getStatus()
        );
    }

    public Order toModelWithChefId(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getClientId(),
                entity.getRestaurantId(),
                entity.getDishes().stream()
                        .map(d -> new OrderDish(d.getId().getDishId(), d.getQuantity()))
                        .collect(Collectors.toList()),
                entity.getDate(),
                entity.getStatus(),
                entity.getChefId()
        );
    }
}
