package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
}