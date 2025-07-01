package com.plazoleta.msrestaurant.infrastructure.output.jpa.repository;

import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByClientIdAndStatusIn(Long clientId, List<OrderStatus> statuses);
}
