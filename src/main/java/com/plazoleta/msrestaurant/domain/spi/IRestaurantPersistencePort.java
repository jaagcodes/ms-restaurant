package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.Restaurant;

import java.util.Optional;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

    Optional<Restaurant> findByNit(String nit);

    Optional<Restaurant> findById(Long id);
}
