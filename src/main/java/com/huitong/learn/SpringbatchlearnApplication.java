package com.huitong.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbatchlearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchlearnApplication.class, args);
	}

	//TODO List

	//5. think about how to figure the concurrent issue -> use Geode?
	//6. partial record lock -> google guava striped lock
}
