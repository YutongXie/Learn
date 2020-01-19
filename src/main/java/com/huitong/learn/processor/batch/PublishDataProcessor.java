package com.huitong.learn.processor.batch;

import com.huitong.learn.entity.Person;
import com.huitong.learn.kafka.KafkaSender;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class PublishDataProcessor implements ItemProcessor<Person, Person> {
    @Autowired
    private KafkaSender kafkaSender;
    @Override
    public Person process(Person person) throws Exception {
        kafkaSender.sendMessage(person);
        System.out.println("published message to Kafka" + person.toString());
        return person;
    }
}
