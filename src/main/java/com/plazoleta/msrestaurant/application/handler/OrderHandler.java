package com.plazoleta.msrestaurant.application.handler;

import com.plazoleta.msrestaurant.application.dto.CreateOrderRequest;
import com.plazoleta.msrestaurant.application.dto.OrderResponse;
import com.plazoleta.msrestaurant.application.dto.PaginatedOrderResponse;
import com.plazoleta.msrestaurant.application.dto.TakeOrderResponse;
import com.plazoleta.msrestaurant.application.mapper.OrderRequestMapper;
import com.plazoleta.msrestaurant.application.mapper.OrderResponseMapper;
import com.plazoleta.msrestaurant.domain.api.IOrderServicePort;
import com.plazoleta.msrestaurant.domain.model.Order;
import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public PaginatedOrderResponse getOrdersByStatus( String status, Long restaurantId, int page, int size) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        Page<Order> ordersPage = orderServicePort.getOrdersByStatus(orderStatus, restaurantId, page, size);
        log.info("[Handler] ordersPage: {}", ordersPage.toString());

        List<OrderResponse> orderResponses = ordersPage.getContent().stream()
                .map(orderResponseMapper::toResponse)
                .collect(Collectors.toList());

        PaginatedOrderResponse response = new PaginatedOrderResponse();
        response.setOrders(orderResponses);
        response.setCurrentPage(ordersPage.getNumber());
        response.setTotalPages(ordersPage.getTotalPages());
        response.setTotalElements(ordersPage.getTotalElements());

        return response;
    }

    @Override
    public TakeOrderResponse takeOrder(Long orderId) {
        Order updatedOrder = orderServicePort.takeOrder(orderId);
        return orderResponseMapper.toTakeOrderResponse(updatedOrder);
    }

    @Override
    public OrderResponse markOrderReady(Long orderId) {
        log.info("🔄 [Order Handler] mark as ready order: {}", orderId);
        Order order = orderServicePort.markOrderReady(orderId);
        log.info("🔄 [Order Handler] marked as READY order: {}", order);
        return orderResponseMapper.toResponse(order);
    }

    @Override
    public OrderResponse markOrderDelivered(Long orderId, String providedPIN) {
        log.info("🔄 [Order Handler] mark as delivered order: {}", orderId);
        Order order = orderServicePort.markOrderDelivered(orderId,  providedPIN);
        log.info("🔄 [Order Handler] marked as DELIVERED order: {}", order);
        return orderResponseMapper.toResponse(order);
    }

    @Override
    public OrderResponse markOrderCanceled(Long orderId) {
        log.info("🔄 [Order Handler] mark as canceled order: {}", orderId);
        Order order = orderServicePort.markOrderCanceled(orderId);
        log.info("🔄 [Order Handler] marked as CANCELED order: {}", order);
        return orderResponseMapper.toResponse(order);
    }
}
