package com.plazoleta.msrestaurant.application.dto;

public class OrderDeliveredRequest {

    private String providedPin;

    public OrderDeliveredRequest() {}

    public OrderDeliveredRequest(String providedPin) {
        this.providedPin = providedPin;
    }

    public void setProvidedPin(String providedPin) {
        this.providedPin = providedPin;
    }

    public String getProvidedPin() {
        return this.providedPin;
    }

    @Override
    public String toString() {
        return "OrderDeliveredRequest{" +
                "providedPin='" + providedPin + '\'' +
                '}';
    }
}
