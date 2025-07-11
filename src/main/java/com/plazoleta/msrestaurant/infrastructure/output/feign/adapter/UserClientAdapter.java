package com.plazoleta.msrestaurant.infrastructure.output.feign.adapter;

import com.plazoleta.msrestaurant.application.dto.UserDto;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.*;
import com.plazoleta.msrestaurant.infrastructure.output.feign.client.UserClient;
import com.plazoleta.msrestaurant.domain.model.User;
import com.plazoleta.msrestaurant.infrastructure.output.feign.mapper.UserResponseMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientAdapter implements IUserClientPort {

    private static final Logger logger = LoggerFactory.getLogger(UserClientAdapter.class);
    private final UserClient userClient;
    private final UserResponseMapper userResponseMapper;

    @Override
    public boolean isUserOwner(Long id){
        logger.info("Validating if user with ID {} has OWNER role", id);
        try{
            boolean isOwner = userClient.isUserOwner(id);
            logger.info("User {} isOwner: {}", id, isOwner);
            return isOwner;
        } catch (FeignException.NotFound e) {
            logger.warn("User with ID {} not found in User Service", id);
            throw new OwnerValidationException();
        } catch (FeignException.Forbidden e) {
            logger.warn("User with does not have permission", id);
            throw new ForbiddenException();
        } catch (FeignException e) {
            logger.error("Unexpected error calling User Service: status={} message={}", e.status(), e.getMessage());
            throw new ApplicationException("Unexpected error consuming users service: "+ e.status(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isEmployeeOfRestaurant(Long employeeId, Long restaurantId) {
        logger.info("Validating if employee with ID {} is employee of restaurant with ID {}", employeeId, restaurantId);
        try{
            return userClient.isEmployeeOfRestaurant(employeeId, restaurantId);
        } catch (FeignException.NotFound e) {
            logger.warn("Employee with ID {} not found in User Service", employeeId);
            throw new OwnerValidationException();
        } catch (FeignException.Forbidden e) {
            logger.warn("User with ID {} does not have permission", employeeId);
            throw new ForbiddenException();
        } catch (FeignException e) {
            logger.error("Unexpected error calling User Service: status={} message={}", e.status(), e.getMessage());
            throw new ApplicationException("Unexpected error consuming users service: "+ e.status(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User getUserById(Long userId) {
        logger.info("[UserClientAdapter] Requesting client by ClientId {}", userId);
        try{
            User user = userResponseMapper.toDomain(userClient.getUserById(userId));
            logger.info("[UserClientAdapter] User = {} ", user);
            return user;
        } catch (FeignException.NotFound e) {
            logger.warn("User with ID {} not found in User Service", userId);
            throw new UserNotFoundException();
        } catch (FeignException e) {
            logger.error("Unexpected error calling Users Service: status={} message={} exception={}", e.status(), e.getMessage(), e.toString());
            throw new ApplicationException("Unexpected error consuming Users service: " + e.status(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
