package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateDishRequest;
import com.plazoleta.msrestaurant.application.dto.DishResponse;
import com.plazoleta.msrestaurant.application.dto.UpdateDishRequest;
import com.plazoleta.msrestaurant.application.dto.UpdateDishStatusRequest;
import com.plazoleta.msrestaurant.application.mapper.DishRequestMapper;
import com.plazoleta.msrestaurant.application.mapper.DishResponseMapper;
import com.plazoleta.msrestaurant.domain.api.IDishServicePort;
import com.plazoleta.msrestaurant.domain.model.Dish;
import com.plazoleta.msrestaurant.infrastructure.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private static final Logger log = LoggerFactory.getLogger(DishHandler.class);
    private final IDishServicePort dishServicePort;
    private final DishRequestMapper dishRequestMapper;
    private final DishResponseMapper dishResponseMapper;

    @Override
    public void createDish(CreateDishRequest request) {
        log.debug(" Mapping CreateDishRequest to domain model: {}", request.getName());
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long ownerId = user.id();
        Dish dish = dishRequestMapper.toDish(request, ownerId);
        log.debug(" Invoking use case to create dish: {}", dish.getName());
        dishServicePort.createDish(dish);
        log.info("✅ Dish '{}' processed successfully by handler", dish.getName());
    }

    @Override
    public void updateDish( Long id, UpdateDishRequest request) {
        log.debug(" Mapping UpdateDishRequest to domain model: {}", request.toString());
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long ownerId = user.id();

        Dish dish = new Dish(
                id,
                null, // name
                request.getPrice(),
                request.getDescription(),
                null, // imageUrl
                null, // categoryId
                null, // restaurantId
                null, // active
                ownerId
        );
        dishServicePort.updateDish(dish);
        log.info("✅ Dish ID '{}' updated successfully by handler", dish.getId());
    }

    @Override
    public void updateDishStatus(Long dishId, UpdateDishStatusRequest request) {
        dishServicePort.updateDishStatus(dishId, request.getActive());
    }

    @Override
    public Page<DishResponse> getDishesByRestaurant(Long restaurantId, Long categoryId, int page, int size) {
        Page<Dish> dishesPage = dishServicePort.getDishesByRestaurant(restaurantId, categoryId, page, size);
        List<DishResponse> responseList = dishResponseMapper.toResponseList(dishesPage.getContent());
        return new PageImpl<>(responseList, dishesPage.getPageable(), dishesPage.getTotalElements());
    }
}
