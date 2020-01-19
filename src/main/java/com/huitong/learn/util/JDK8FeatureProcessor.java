package com.huitong.learn.util;

import com.huitong.learn.entity.Person;

import java.util.Optional;

public class JDK8FeatureProcessor {

    private Person person;
    public Person getPerson() {
//        person = new Person();
        Optional<Person> optionalPerson = Optional.ofNullable(person);
        if(optionalPerson.isPresent()) {//if not null, will go into below block
            System.out.println(optionalPerson.get().toString());
        } else {

        }
        optionalPerson.ofNullable(person).orElseThrow(() -> new RuntimeException());
        //even person is not null, still will execute createDefaultPerson(). may cause performance issue
        Person existPerson = optionalPerson.ofNullable(person).orElse(createDefaultPerson("orElse"));
        //if person is not null, will not execute createDefaultPerson().
        Person existPerson2 = optionalPerson.ofNullable(person).orElseGet(() -> createDefaultPerson("orElseGet"));
        return existPerson;
    }

    public Person createDefaultPerson(String from) {
        System.out.println("Creating default person from:" + from);
        Person person = new Person();
        person.setLastName("default");
        person.setFirstName("default");
        person.setAddress("default");
        return person;
    }

    public String convert() {
        String firstName = Optional.ofNullable(person).map(p -> p.getFirstName())
                .orElse(createDefaultPerson("orElse").getFirstName());
        return firstName;
    }

    public Person filter() {
       Optional<Person> optionalPerson = Optional.ofNullable(person)
               .filter(p -> p.getFirstName() != null && !"".equals(p.getFirstName()));
       return optionalPerson.isPresent() ? optionalPerson.get() : null;
    }

    public static void main(String[] args) {
        JDK8FeatureProcessor jdk8FeatureProcessor = new JDK8FeatureProcessor();
        jdk8FeatureProcessor.getPerson();
    }
}
