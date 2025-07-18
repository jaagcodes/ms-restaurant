package com.plazoleta.msrestaurant.infrastructure.configuration;

import com.plazoleta.msrestaurant.application.mapper.DishRequestMapper;
import com.plazoleta.msrestaurant.application.mapper.OrderRequestMapper;
import com.plazoleta.msrestaurant.application.mapper.OrderResponseMapper;
import com.plazoleta.msrestaurant.application.mapper.RestaurantRequestMapper;
import com.plazoleta.msrestaurant.domain.api.IDishServicePort;
import com.plazoleta.msrestaurant.domain.api.IOrderServicePort;
import com.plazoleta.msrestaurant.domain.api.IRestaurantServicePort;
import com.plazoleta.msrestaurant.domain.api.ISecurityServicePort;
import com.plazoleta.msrestaurant.domain.spi.*;
import com.plazoleta.msrestaurant.domain.usecase.DishUseCase;
import com.plazoleta.msrestaurant.domain.usecase.OrderUseCase;
import com.plazoleta.msrestaurant.domain.usecase.RestaurantUseCase;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter.OrderJpaAdapter;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IDishRepository;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IOrderRepository;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IRestaurantRepository;
import com.plazoleta.msrestaurant.infrastructure.security.adapter.SecurityServiceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DishRequestMapper dishRequestMapper() {
        return new DishRequestMapper();
    }

    @Bean
    public ISecurityServicePort securityServicePort() {
        return new SecurityServiceAdapter();
    }

    @Bean
    public IDishServicePort dishServicePort(
            IDishPersistencePort dishPersistencePort,
            IRestaurantPersistencePort restaurantPersistencePort,
            ISecurityServicePort securityServicePort,
            IRestaurantServicePort restaurantServicePort
    ) {
        return new DishUseCase(dishPersistencePort, restaurantPersistencePort, securityServicePort, restaurantServicePort);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(
            IDishRepository dishRepository,
            DishEntityMapper dishEntityMapper
    ) {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public RestaurantRequestMapper restaurantRequestMapper() {
        return new RestaurantRequestMapper();
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(
            IRestaurantPersistencePort persistencePort,
            IUserClientPort userClientPort
    ) {
        return new RestaurantUseCase(persistencePort, userClientPort);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(
            IRestaurantRepository repo,
            RestaurantEntityMapper mapper
    ) {
        return new RestaurantJpaAdapter(repo, mapper);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(
            IOrderRepository repository,
            OrderEntityMapper mapper
    ) {
        return new OrderJpaAdapter(repository, mapper);
    }

    @Bean
    public OrderRequestMapper orderRequestMapper() {
        return new OrderRequestMapper();
    }

    @Bean
    public OrderResponseMapper orderResponseMapper() {
        return new OrderResponseMapper();
    }

    @Bean
    public IOrderServicePort orderServicePort(
            IOrderPersistencePort orderPersistencePort,
            IDishPersistencePort dishPersistencePort,
            ISecurityServicePort securityServicePort,
            IUserClientPort userClientPort,
            ISmsClientPort smsClientPort
    ) {
        return new OrderUseCase(orderPersistencePort, dishPersistencePort, securityServicePort, userClientPort, smsClientPort);
    }


}
