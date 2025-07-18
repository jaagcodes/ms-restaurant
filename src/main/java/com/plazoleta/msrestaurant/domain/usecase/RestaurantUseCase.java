package com.plazoleta.msrestaurant.domain.usecase;

import com.plazoleta.msrestaurant.domain.api.IRestaurantServicePort;
import com.plazoleta.msrestaurant.domain.model.Restaurant;
import com.plazoleta.msrestaurant.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.OwnerNotValidException;
import com.plazoleta.msrestaurant.infrastructure.exception.RestaurantAlreadyExistsException;
import com.plazoleta.msrestaurant.infrastructure.exception.RestaurantNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class RestaurantUseCase implements IRestaurantServicePort {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantUseCase.class);
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        logger.info("Creating restaurant with NIT: {} and ownerId: {}", restaurant.getNit(), restaurant.getOwnerId());

        if(!userClientPort.isUserOwner(restaurant.getOwnerId())) {
            logger.warn("User with ID {} is not a valid owner", restaurant.getOwnerId());
            throw new OwnerNotValidException();
        }

        Optional<Restaurant> existing = restaurantPersistencePort.findByNit(restaurant.getNit());
        if (existing.isPresent()) {
            logger.warn("Restaurant with NIT {} already exists", restaurant.getNit());
            throw new RestaurantAlreadyExistsException();
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
        logger.info("Restaurant successfully created with NIT: {}", restaurant.getNit());
    }

    @Override
    public boolean isOwnerOfRestaurant(Long userId, Long restaurantId) {
        logger.info("Searching restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantPersistencePort.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException());

        return restaurant.getOwnerId().equals(userId);
    }

    @Override
    public List<Restaurant> getAllRestaurants(int page, int size) {
        return restaurantPersistencePort.getAllRestaurants(page, size);
    }
}
