package com.imart.order.kafka.producer;


import com.imart.order.exception.KafkaDeliveryException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaProducer {
    private static final Marker KAFKA_PUBLICATION_SUCCESS = MarkerFactory.getMarker("KAFKA_PUBLICATION_SUCCESS");
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishEvent(String topic, Object message) throws KafkaDeliveryException {
        kafkaTemplate.send(topic, message).whenComplete((result,ex) -> {
            if(ex != null){
                throw new KafkaDeliveryException("kafka delivery failure", topic, ex.getCause());
            }
        });
        logger.info(KAFKA_PUBLICATION_SUCCESS, topic, message);
    }

}