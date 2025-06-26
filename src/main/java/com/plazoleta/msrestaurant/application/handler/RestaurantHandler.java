package com.plazoleta.msrestaurant.application.handler;


import com.plazoleta.msrestaurant.application.dto.CreateRestaurantRequest;
import com.plazoleta.msrestaurant.application.mapper.RestaurantRequestMapper;
import com.plazoleta.msrestaurant.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantRequestMapper restaurantRequestMapper;

    @Override
    public void createRestaurant(CreateRestaurantRequest request) {
        restaurantServicePort.createRestaurant(restaurantRequestMapper.toRestaurant(request));
    }

    @Override
    public boolean isOwnerOfRestaurant(Long userId, Long restaurantId) {
        return restaurantServicePort.isOwnerOfRestaurant(userId, restaurantId);
    }
}