package com.huitong.learn.processor.batch;

import com.huitong.learn.entity.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        String firstName = person.getFirstName().toUpperCase();
        String lastName = person.getLastName().toUpperCase();

//        Person newPerson = new Person(firstName, lastName);
////        newPerson.setFirstName(firstName);
////        newPerson.setLastName(lastName);

        person.setFirstName(person.getFirstName().toUpperCase());
        person.setLastName(person.getLastName().toUpperCase());
        return person;

    }
}
