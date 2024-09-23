package com.example.kafkaassignment.controller;


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
    public String sendMessage(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String dob) {
        String message = String.format("%s,%s,%s", firstName, lastName, dob);
        kafkaTemplate.send("CustomerInputTopic", message);
        return "Message sent to Kafka: " + message;
    }
}

