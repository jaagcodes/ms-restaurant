package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;
import com.plazoleta.msrestaurant.application.mapper.DishRequestMapper;
import com.plazoleta.msrestaurant.domain.api.IDishServicePort;
import com.plazoleta.msrestaurant.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private static final Logger log = LoggerFactory.getLogger(DishHandler.class);
    private final IDishServicePort dishServicePort;
    private final DishRequestMapper dishRequestMapper;

    @Override
    public void createDish(CreateDishRequest request) {
        log.debug(" Mapping CreateDishRequest to domain model: {}", request.getName());
        Dish dish = dishRequestMapper.toDish(request);
        log.debug(" Invoking use case to create dish: {}", dish.getName());
        dishServicePort.createDish(dish);
        log.info("✅ Dish '{}' processed successfully by handler", dish.getName());
    }

    @Override
    public void updateDish( Long id, UpdateDishRequest request) {
        log.debug(" Mapping UpdateDishRequest to domain model: {}", request.toString());
        Dish dish = new Dish(
                id,
                null, // name
                request.getPrice(),
                request.getDescription(),
                null, // imageUrl
                null, // categoryId
                null, // restaurantId
                null, // active
                request.getOwnerId()
        );
        dishServicePort.updateDish(dish);
        log.info("✅ Dish ID '{}' updated successfully by handler", dish.getId());
    }
}
