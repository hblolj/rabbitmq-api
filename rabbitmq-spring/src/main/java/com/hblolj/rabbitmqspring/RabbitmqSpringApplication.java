package com.hblolj.rabbitmqspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.hblolj.rabbitmqspring")
@SpringBootApplication
public class RabbitmqSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqSpringApplication.class, args);
	}
}
