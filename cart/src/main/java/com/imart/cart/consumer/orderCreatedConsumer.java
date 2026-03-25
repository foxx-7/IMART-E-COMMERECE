package com.imart.cart.consumer;

import com.imart.cart.dto.foreign.OrderCreatedEvent;
import com.imart.cart.repository.CartItemRepository;
import com.imart.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class orderCreatedConsumer {
    private static final Logger logger = LoggerFactory.getLogger(orderCreatedConsumer.class);
    private final Marker KAFKA_RECEPTION_SUCCESS = MarkerFactory.getMarker("ORDER_CREATED");
    private final CartService cartService;

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consumeOrderCreated(@Payload OrderCreatedEvent orderEvent, @Header(KafkaHeaders.RECEIVED_TOPIC ) String topic){
        cartService.clearCart(orderEvent.getUserId());
        logger.info(KAFKA_RECEPTION_SUCCESS, topic, orderEvent);
    }
}