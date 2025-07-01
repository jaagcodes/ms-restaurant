package com.plazoleta.msrestaurant.infrastructure.output.jpa.repository;

import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderDishEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderDishId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, OrderDishId> {
}

