package com.example.kafkaassignment.service;

import com.example.kafkaassignment.enums.KafkaTopics;
import com.example.kafkaassignment.model.Person;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class PersonMessageHandlerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PersonMessageHandlerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "CustomerInputTopic", groupId = "group_id")
    public void consume(String message) {
        String[] parts = message.split(",");
        String firstName = parts[0];
        String lastName = parts[1];
        String dob = parts[2];

        int age = calculateAge(dob);
        System.out.println("Consumed message: " + message + ", Calculated Age: " + age);

        if (age % 2 == 0) {
            kafkaTemplate.send(KafkaTopics.CUSTOMER_EVEN.getTopicName(), message);
            System.out.println("Published to CustomerEVEN topic: " + message);
        } else {
            kafkaTemplate.send(KafkaTopics.CUSTOMER_ODD.getTopicName(), message);
            System.out.println("Published to CustomerODD topic: " + message);
        }
    }

    private int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private void publishMessageBasedOnPersonAge(Person person) {

    }
}

