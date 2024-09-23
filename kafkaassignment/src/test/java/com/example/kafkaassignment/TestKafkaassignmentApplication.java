package com.example.kafkaassignment;

import org.springframework.boot.SpringApplication;

public class TestKafkaassignmentApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkaassignmentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
