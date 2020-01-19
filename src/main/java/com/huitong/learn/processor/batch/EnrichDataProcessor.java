package com.huitong.learn.processor.batch;

import com.huitong.learn.entity.Person;
import org.springframework.batch.item.ItemProcessor;

public class EnrichDataProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        person.setAddress("Citi Office-XXX");
        return person;
    }
}
