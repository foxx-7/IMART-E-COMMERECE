package com.imart.order.consumer;

import com.imart.order.dto.local.CheckOutEvent;
import com.imart.order.service.OrderService;
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
public class CheckOutConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CheckOutConsumer.class);
    private final Marker KAFKA_RECEPTION_SUCCESS = MarkerFactory.getMarker("KAFKA_RECEPTION_SUCCESS");
    private final OrderService orderService;

    @KafkaListener(topics = "checkout-event", groupId = "checkout-group")
    public void consumeOrderCreated(@Payload CheckOutEvent event, @Header(KafkaHeaders.RECEIVED_TOPIC ) String topic){
        InitiatedOrder initiatedOrder = new InitiatedOrder();
        initiatedOrder.setCart(event.getCart());
        orderService.initiateOrder(initiatedOrder, event.getCart().getUserId());
        logger.info(KAFKA_RECEPTION_SUCCESS, topic, event);
    }
}
