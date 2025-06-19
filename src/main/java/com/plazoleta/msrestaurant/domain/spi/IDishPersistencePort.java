package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.Dish;

import java.util.Optional;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Optional<Dish> findById(Long id);
}