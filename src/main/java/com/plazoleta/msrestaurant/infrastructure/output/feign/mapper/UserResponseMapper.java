package com.plazoleta.msrestaurant.infrastructure.output.feign.mapper;

import com.plazoleta.msrestaurant.application.dto.UserDto;
import com.plazoleta.msrestaurant.domain.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserResponseMapper {

    public User toDomain(UserDto dto) {
        return new User(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getDocumentNumber(),
                dto.getPhoneNumber(),
                dto.getBirthDate() != null ? LocalDate.parse(dto.getBirthDate()) : null,
                null, // email
                null, // password
                null, // role
                null  // restaurantId
        );
    }
}
