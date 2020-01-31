package com.huitong.learn.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class KafkaConsumer {

//    @KafkaListener(topics = {"com.xyt.kafka.test"})
    public void consume(ConsumerRecord consumerRecord) {
//       Optional message = Optional.ofNullable(consumerRecord.value());
//       if(message.isPresent()) {
//           Object obj = message.get();
//           System.out.println("Consume message from Kafka:" + obj);
//       }
    }
}
