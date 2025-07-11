package com.plazoleta.msrestaurant.infrastructure.output.feign.client;

import com.plazoleta.msrestaurant.domain.model.SendSms;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="smsClient", url = "${sms.service.url}")
public interface SmsClient {
    @PostMapping("/sms/send-sms")
    String sendSms(@RequestBody(required = true) SendSms sendSms);
}
