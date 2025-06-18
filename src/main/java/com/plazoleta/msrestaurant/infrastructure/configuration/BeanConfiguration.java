package com.plazoleta.msrestaurant.infrastructure.configuration;

import com.plazoleta.msrestaurant.application.mapper.RestaurantRequestMapper;
import com.plazoleta.msrestaurant.domain.api.IRestaurantServicePort;
import com.plazoleta.msrestaurant.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.domain.usecase.RestaurantUseCase;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IRestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestaurantRequestMapper restaurantRequestMapper() {
        return new RestaurantRequestMapper();
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(
            IRestaurantPersistencePort persistencePort,
            IUserClientPort userClientPort) {
        return new RestaurantUseCase(persistencePort, userClientPort);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(
            IRestaurantRepository repo,
            RestaurantEntityMapper mapper) {
        return new RestaurantJpaAdapter(repo, mapper);
    }

}
