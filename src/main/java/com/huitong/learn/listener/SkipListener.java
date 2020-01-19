package com.huitong.learn.listener;

import com.huitong.learn.entity.Person;

public class SkipListener implements org.springframework.batch.core.SkipListener<Person, Person> {
    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.print("Failed at reading records. Skip it:");
    }

    @Override
    public void onSkipInWrite(Person person, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(Person person, Throwable throwable) {
        System.out.println("Process skipped record:" + person.toString());
    }
}
