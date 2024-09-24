package com.example.kafkaassignment.service;

import com.example.kafkaassignment.template.KafkaMessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PersonMessageHandlerServiceTest {

    private PersonMessageHandlerService personMessageHandlerService;
    private KafkaMessageProducer kafkaMessageProducerMock;

    @BeforeEach
    public void setUp() {
        kafkaMessageProducerMock = Mockito.mock(KafkaMessageProducer.class);
        personMessageHandlerService = new PersonMessageHandlerService(kafkaMessageProducerMock);
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
