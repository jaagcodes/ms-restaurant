package com.plazoleta.msrestaurant.domain.model;

public class SendSms {

    private String to;
    private String message;

    public SendSms(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendSms{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
