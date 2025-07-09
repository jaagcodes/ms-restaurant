package com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter;

import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import com.plazoleta.msrestaurant.domain.spi.IOrderPersistencePort;
import com.plazoleta.msrestaurant.infrastructure.exception.OrderNotFoundException;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.OrderEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    Logger log = LoggerFactory.getLogger(OrderJpaAdapter.class);

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        OrderEntity savedEntity = orderRepository.save(orderEntity);
        log.info("Order saved with id {}", savedEntity);
        return orderEntityMapper.toModel(savedEntity);
    }

    public Order updateOrder(Order order) {
        OrderEntity existingEntity = orderRepository.findById(order.getId())
                .orElseThrow(OrderNotFoundException::new);

        OrderEntity updatedEntity = orderEntityMapper.toEntityWithoutDishes(order);
        updatedEntity.setDishes(existingEntity.getDishes());

        OrderEntity savedEntity = orderRepository.save(updatedEntity);
        return orderEntityMapper.toModelWithChefId(savedEntity);
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

    @Override
    public Page<Order> findByStatusAndRestaurant(OrderStatus status, Long restaurantId, int page, int size) {
        Page<OrderEntity> resultPage = orderRepository.findByRestaurantIdAndStatus(restaurantId, status, PageRequest.of(page, size));
        List<Order> orderList = resultPage.getContent()
                .stream()
                .map(orderEntityMapper::toModel)
                .collect(Collectors.toList());

        return new PageImpl<>(orderList, resultPage.getPageable(), resultPage.getTotalElements());
    }

    @Override
    public Order findById(Long orderId) {
       return orderRepository.findById(orderId)
               .map(orderEntityMapper::toModel)
               .orElse(null);
    }

}
