package com.example.kafkaassignment.service;

import com.example.kafkaassignment.controller.PersonController;
import com.example.kafkaassignment.enums.KafkaTopics;
import com.example.kafkaassignment.template.KafkaMessageProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class PersonMessageHandlerService {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private final KafkaMessageProducer kafkaMessageProducer;

    public PersonMessageHandlerService(KafkaMessageProducer kafkaMessageProducer) {
        this.kafkaMessageProducer = kafkaMessageProducer;
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

    public int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void publishMessageBasedOnPersonAge(int age, String consumedMessage) {
        if (age % 2 == 0) {
            kafkaMessageProducer.sendMessage(KafkaTopics.CUSTOMER_EVEN.getTopicName(), consumedMessage);
            logger.info("Published to CustomerEVEN topic: " + consumedMessage);
        } else {
            kafkaMessageProducer.sendMessage(KafkaTopics.CUSTOMER_ODD.getTopicName(), consumedMessage);
            logger.info("Published to CustomerODD topic: " + consumedMessage);
        }
    }
}

