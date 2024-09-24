package com.example.kafkaassignment.service;

import com.example.kafkaassignment.controller.PersonController;
import com.example.kafkaassignment.enums.KafkaTopics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class PersonMessageHandlerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LogManager.getLogger(PersonController.class);

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
        logger.info("Consumed message: " + message + ", Calculated Age: " + age);
        publishMessageBasedOnPersonAge(age, message);
    }

    private int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private void publishMessageBasedOnPersonAge(int age, String consumedMessage) {
        if (age % 2 == 0) {
            kafkaTemplate.send(KafkaTopics.CUSTOMER_EVEN.getTopicName(), consumedMessage);
            logger.info("Published to CustomerEVEN topic: " + consumedMessage);
        } else {
            kafkaTemplate.send(KafkaTopics.CUSTOMER_ODD.getTopicName(), consumedMessage);
            logger.info("Published to CustomerODD topic: " + consumedMessage);
        }
    }
}

