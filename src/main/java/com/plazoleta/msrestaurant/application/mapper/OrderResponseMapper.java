package com.plazoleta.msrestaurant.application.mapper;

import com.plazoleta.msrestaurant.application.dto.DishQuantityResponse;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.dto.TakeOrderResponse;
import com.plazoleta.msrestaurant.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseMapper {
    private static final Logger logger = LoggerFactory.getLogger(OrderResponseMapper.class);

    public OrderResponse toResponse(Order order) {
        logger.info("[OrderResponseMapper] order: {}", order.toString());
        List<DishQuantityResponse> dishes = order.getDishes().stream()
                .map(d -> new DishQuantityResponse(d.getDishId(), d.getQuantity()))
                .collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setClientId(order.getClientId());
        response.setRestaurantId(order.getRestaurantId());
        response.setChefId(order.getChefId());
        response.setDate(order.getDate());
        response.setStatus(order.getStatus().name());
        response.setDishes(dishes);

        return response;
    }

    public TakeOrderResponse toTakeOrderResponse(Order order) {
        TakeOrderResponse response = new TakeOrderResponse();
        response.setId(order.getId());
        response.setClientId(order.getClientId());
        response.setRestaurantId(order.getRestaurantId());
        response.setChefId(order.getChefId());
        response.setDate(order.getDate());
        response.setStatus(order.getStatus().name());
        response.setDishes(
                order.getDishes().stream()
                        .map(d -> new DishQuantityResponse(d.getDishId(), d.getQuantity()))
                        .collect(Collectors.toList())
        );
        return response;
    }

}
