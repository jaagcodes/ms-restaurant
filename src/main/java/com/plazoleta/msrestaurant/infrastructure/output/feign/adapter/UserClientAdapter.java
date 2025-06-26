package com.plazoleta.msrestaurant.infrastructure.output.feign.adapter;

import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.ApplicationException;
import com.plazoleta.msrestaurant.infrastructure.exception.ForbiddenException;
import com.plazoleta.msrestaurant.infrastructure.exception.OwnerNotValidException;
import com.plazoleta.msrestaurant.infrastructure.exception.OwnerValidationException;
import com.plazoleta.msrestaurant.infrastructure.output.feign.client.UserClient;
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
}
