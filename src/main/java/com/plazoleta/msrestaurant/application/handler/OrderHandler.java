package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.mapper.OrderRequestMapper;
import com.plazoleta.msrestaurant.application.mapper.OrderResponseMapper;
import com.plazoleta.msrestaurant.domain.api.IOrderServicePort;
import com.plazoleta.msrestaurant.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderHandler.class);
    private final IOrderServicePort orderServicePort;
    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = orderRequestMapper.toModel(request);
        log.info("🔄 [Order Handler] mapped request: {}  order: {}", request.toString(), order.toString());
        Order savedOrder = orderServicePort.createOrder(order);
        return orderResponseMapper.toResponse(savedOrder);
    }
}
