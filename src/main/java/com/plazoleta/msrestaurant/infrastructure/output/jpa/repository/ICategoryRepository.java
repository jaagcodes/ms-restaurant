package com.plazoleta.msrestaurant.infrastructure.output.jpa.repository;

import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByName(String name);
}
