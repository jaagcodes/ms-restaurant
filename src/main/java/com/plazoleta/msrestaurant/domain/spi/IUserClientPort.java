package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.User;

public interface IUserClientPort {
    boolean isUserOwner(Long id);
    boolean isEmployeeOfRestaurant(Long employeeId, Long restaurantId);
    User getUserById(Long userId);
}
