package com.plazoleta.msrestaurant.domain.spi;

import com.plazoleta.msrestaurant.domain.model.SendSms;

public interface ISmsClientPort {
    void sendSms(SendSms sendSms);
}
