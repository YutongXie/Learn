package com.huitong.learn.kafka;

import com.huitong.learn.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(Person person) {
        kafkaTemplate.send("com.xyt.kafka.test", person.toString());
    }
}
