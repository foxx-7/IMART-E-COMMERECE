package com.imart.order.exception;

import lombok.Getter;

@Getter
public class KafkaDeliveryException extends RuntimeException {
    private final String topic;
    public KafkaDeliveryException(String message, String topic, Throwable cause) {
        super(message, cause);
        this.topic = topic;
    }
}
