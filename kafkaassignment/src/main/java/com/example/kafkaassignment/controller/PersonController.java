package com.example.kafkaassignment.controller;


import com.example.kafkaassignment.enums.KafkaTopics;
import com.example.kafkaassignment.model.Person;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PersonController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Person person) {
        String message = String.format("%s,%s,%s", person.firstName(), person.lastName(), person.dateOfBirth());
        kafkaTemplate.send(KafkaTopics.CUSTOMER_INPUT.getTopicName(), message);
        return "Message sent to Kafka: " + message;
    }
}

