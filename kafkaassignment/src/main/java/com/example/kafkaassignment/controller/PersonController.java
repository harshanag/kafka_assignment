package com.example.kafkaassignment.controller;


import com.example.kafkaassignment.enums.KafkaTopics;
import com.example.kafkaassignment.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LogManager.getLogger(PersonController.class);

    public PersonController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Person person) {
        String message = String.format("%s,%s,%s", person.firstName(), person.lastName(), person.dateOfBirth());
        kafkaTemplate.send(KafkaTopics.CUSTOMER_INPUT.getTopicName(), message);
        logger.info("Message published successfully");
        return "Message sent to Kafka: " + message;
    }
}

