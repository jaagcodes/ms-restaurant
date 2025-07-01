package com.plazoleta.msrestaurant.infrastructure.output.jpa.adapter;

import com.plazoleta.msrestaurant.domain.model.Restaurant;
import com.plazoleta.msrestaurant.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.RestaurantEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantEntity entity = restaurantEntityMapper.toEntity(restaurant);
        restaurantRepository.save(entity);
    }

    @Override
    public Optional<Restaurant> findByNit(String nit) {
        return restaurantRepository.findByNit(nit)
                .map(restaurantEntityMapper::toDomain);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::toDomain);
    }

    @Override
    public List<Restaurant> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return restaurantRepository.findAll(pageable).stream()
                .map(restaurantEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

}
