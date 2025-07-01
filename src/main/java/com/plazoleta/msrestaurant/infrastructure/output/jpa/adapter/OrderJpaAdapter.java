package com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter;

import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.spi.IOrderPersistencePort;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        OrderEntity savedEntity = orderRepository.save(orderEntity);
        return orderEntityMapper.toModel(savedEntity);
    }

    @Override
    public boolean hasActiveOrder(Long clientId) {
        return orderRepository.existsByClientIdAndStatusIn(clientId,
                java.util.List.of(
                        com.plazoleta.msrestaurant.domain.model.OrderStatus.PENDING,
                        com.plazoleta.msrestaurant.domain.model.OrderStatus.IN_PREPARATION,
                        com.plazoleta.msrestaurant.domain.model.OrderStatus.READY
                )
        );
    }
}
