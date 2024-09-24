package com.example.kafkaassignment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class PersonMessageHandlerServiceTest {

    private PersonMessageHandlerService personMessageHandlerService;
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setUp() {
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        personMessageHandlerService = new PersonMessageHandlerService(kafkaTemplate);
    }


    @Test
    void testCalculateAge() {
        // Given: a date of birth
        String dob = "1990-01-01";

        // When: the age is calculated
        int age = personMessageHandlerService.calculateAge(dob);

        // Then: the correct age should be returned
        assert(age == 32); // Assuming the current year is 2022
    }

}
