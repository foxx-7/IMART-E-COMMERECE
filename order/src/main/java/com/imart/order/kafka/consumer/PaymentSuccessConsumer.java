package com.imart.order.kafka.consumer;

import com.imart.order.dto.local.TransactionRecord;
import com.imart.order.service.CheckOutService;
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
    public class PaymentSuccessConsumer {
        private final Marker KAFKA_RECEPTION_SUCCESS = MarkerFactory.getMarker("PAYMENT_SUCCESS");
        private final  Logger logger = LoggerFactory.getLogger(PaymentSuccessConsumer.class);
        private final OrderService orderService;

        @KafkaListener(topics = "checkout-event", groupId = "checkout-group")
        public void consumeOrderCreated(@Payload TransactionRecord record, @Header(KafkaHeaders.RECEIVED_TOPIC ) String topic){
            orderService.confirmOrder(record);
            logger.info(KAFKA_RECEPTION_SUCCESS, topic, record);
        }
}
