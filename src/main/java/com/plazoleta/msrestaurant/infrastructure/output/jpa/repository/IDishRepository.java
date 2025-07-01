package com.plazoleta.msrestaurant.infrastructure.output.jpa.repository;

import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Page<DishEntity> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId, Pageable pageable);

    Page<DishEntity> findByRestaurantId(Long restaurantId, Pageable pageable);

    boolean existsByIdAndRestaurantId(Long dishId, Long restaurantId);
}
