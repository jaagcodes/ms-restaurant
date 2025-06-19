package com.plazoleta.msrestaurant.domain.usecase;

import com.plazoleta.msrestaurant.domain.api.IDishServicePort;
import com.plazoleta.msrestaurant.domain.model.Dish;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.OwnerNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DishUseCase implements IDishServicePort {

    private static final Logger log = LoggerFactory.getLogger(DishUseCase.class);

    private final IDishPersistencePort dishPersistencePort;
    private final IUserClientPort userClientPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort,
                       IUserClientPort userClientPort,
                       IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.userClientPort = userClientPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void createDish(Dish dish) {
        log.info("Validating owner with ID {}", dish.getOwnerId());

        if(!userClientPort.isUserOwner(dish.getOwnerId())) {
            log.warn("User {} is not an owner", dish.getRestaurantId());
            throw new OwnerNotValidException();
        }
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
}
