package com.plazoleta.msrestaurant.application.mapper;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderDish;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderRequestMapper {

    public Order toModel(CreateOrderRequest request) {
        List<OrderDish> dishList = request.getDishes().stream()
                .map(d -> new OrderDish(d.getDishId(), d.getQuantity()))
                .collect(Collectors.toList());

        return new Order(
                null, // id
                null,// clientId
                request.getRestaurantId(),
                request.getChefId(), // chefId (se asignará después)
                dishList,
                null, // date (se asigna en el usecase)
                null  // status (se asigna en el usecase)
        );
    }
}
