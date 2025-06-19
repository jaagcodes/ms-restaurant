package com.plazoleta.msrestaurant.infrastructure.output.jpa.repository;

import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

}
