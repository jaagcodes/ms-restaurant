package com.plazoleta.msrestaurant.infrastructure.output.feign.adapter;

import com.plazoleta.msrestaurant.domain.model.SendSms;
import com.plazoleta.msrestaurant.domain.spi.ISmsClientPort;
import com.plazoleta.msrestaurant.domain.spi.IUserClientPort;
import com.plazoleta.msrestaurant.infrastructure.exception.ApplicationException;
import com.plazoleta.msrestaurant.infrastructure.exception.ForbiddenException;
import com.plazoleta.msrestaurant.infrastructure.exception.OwnerValidationException;
import com.plazoleta.msrestaurant.infrastructure.output.feign.client.SmsClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsClientAdapter implements ISmsClientPort {

    private static final Logger logger = LoggerFactory.getLogger(SmsClientAdapter.class);
    private final SmsClient smsClient;


    @Override
    public void sendSms(SendSms sendSms) {
        logger.info("[SmsClientAdapter] Consuming Sms Client with body {}", sendSms.toString() );
        try{
            String sentSms = smsClient.sendSms(sendSms);
            logger.info("Sms client response is {}", sentSms);
        } catch (FeignException.Forbidden e) {
            logger.warn("User does not have permission");
            throw new ForbiddenException();
        } catch (FeignException e) {
            logger.error("Unexpected error calling Sms Service: status={} message={}", e.status(), e.getMessage());
            throw new ApplicationException("Unexpected error consuming Sms service: "+ e.status(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
