package com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter;

import com.plazoleta.msrestaurant.domain.model.Dish;
import com.plazoleta.msrestaurant.domain.spi.IDishPersistencePort;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.DishEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Dish> findDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<DishEntity> entities;

        if (categoryId == null) {
            entities = dishRepository.findByRestaurantId(restaurantId, pageable);
        } else {
            entities = dishRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId, pageable);
        }

        return entities.map(dishEntityMapper::toModel);
    }

    @Override
    public boolean isDishFromRestaurant(Long dishId, Long restaurantId) {
        log.debug("Verifying if dish {} belongs to restaurant {}", dishId, restaurantId);
        return dishRepository.existsByIdAndRestaurantId(dishId, restaurantId);
    }

}
