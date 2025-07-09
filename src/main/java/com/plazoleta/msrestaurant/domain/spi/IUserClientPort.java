package com.plazoleta.msrestaurant.domain.spi;

public interface IUserClientPort {
    boolean isUserOwner(Long id);
    boolean isEmployeeOfRestaurant(Long employeeId, Long restaurantId);
}
