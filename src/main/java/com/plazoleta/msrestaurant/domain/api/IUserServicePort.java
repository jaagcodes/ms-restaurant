package com.plazoleta.msrestaurant.domain.api;

public interface IUserServicePort {
    boolean isUserOwner(Long userId);
}
