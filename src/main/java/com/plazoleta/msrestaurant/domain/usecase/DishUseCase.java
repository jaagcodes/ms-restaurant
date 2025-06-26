package com.plazoleta.msrestaurant.domain.usecase;

import com.plazoleta.msrestaurant.domain.api.IDishServicePort;
import com.plazoleta.msrestaurant.domain.model.Dish;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.DishNotFoundException;
import com.plazoleta.msrestaurant.infrastructure.exception.OwnerNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DishUseCase implements IDishServicePort {

    private static final Logger log = LoggerFactory.getLogger(DishUseCase.class);

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort,
                       IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void createDish(Dish dish) {
        log.info("Validating owner with ID {}", dish.getOwnerId());

        var restaurantOpt = restaurantPersistencePort.findById(dish.getRestaurantId());
        if(restaurantOpt.isEmpty() || !restaurantOpt.get().getOwnerId().equals(dish.getOwnerId())){
            log.warn(" Owner ID {} does not match restaurant owner ID {}", dish.getOwnerId(), restaurantOpt.get().getOwnerId());
            throw new OwnerNotValidException();
        }
        // Guardar el plato
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
        log.info(" Dish '{}' successfully created in restaurant ID {}", dish.getName(), dish.getRestaurantId());
    }

    @Override
    public void updateDish(Dish dish) {
        log.info("Validating dish with ID {}", dish.getId());
        Dish existing = dishPersistencePort.findById(dish.getId())
                .orElseThrow(() -> new DishNotFoundException());

        log.info("Validating restaurant with ID {}", existing.getRestaurantId());
        var restaurant = restaurantPersistencePort.findById(existing.getRestaurantId())
                .orElseThrow(() -> new OwnerNotValidException());

        log.info("Validating if restaurant owner ID: {} equals dish owner ID: {}", restaurant.getOwnerId(), dish.getOwnerId());
        if (!restaurant.getOwnerId().equals(dish.getOwnerId())) {
            throw new OwnerNotValidException();
        }

        // Solo actualizamos precio y descripción
        existing.setPrice(dish.getPrice());
        existing.setDescription(dish.getDescription());

        log.info("Dish to update: {}", dish);
        dishPersistencePort.saveDish(existing);
    }
}
