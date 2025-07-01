package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.Dish;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Optional<Dish> findById(Long id);

    Page<Dish> findDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, int page, int size);

    boolean isDishFromRestaurant(Long dishId, Long restaurantId);

}