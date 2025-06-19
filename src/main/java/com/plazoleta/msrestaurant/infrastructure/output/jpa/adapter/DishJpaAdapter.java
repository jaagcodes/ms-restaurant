package com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter;

import com.plazoleta.msrestaurant.domain.model.Dish;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.DishEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private static final Logger log = LoggerFactory.getLogger(DishJpaAdapter.class);
    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        log.debug("Converting domain model to entity: {}", dish.getName());
        DishEntity entity = (dish.getId() == null )
            ? dishEntityMapper.toEntity(dish)
            : dishEntityMapper.toEntityForUpdate(dish);
        dishRepository.save(entity);
        log.info("Dish '{}' saved successfully in the database", dish.getName());
    }

    @Override
    public Optional<Dish> findById(Long id) {
        log.debug("Searching for dish with ID {}", id);
        return dishRepository.findById(id)
                .map(dishEntityMapper::toDomain);
    }
}
