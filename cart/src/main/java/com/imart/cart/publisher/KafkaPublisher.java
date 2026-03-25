package com.imart.cart.publisher;

import com.imart.cart.exception.KafkaDeliveryException;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaPublisher {
    private static final Marker KAFKA_PUBLICATION_SUCCESS = MarkerFactory.getMarker("KAFKA_PUBLICATION_SUCCESS");
    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);
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
