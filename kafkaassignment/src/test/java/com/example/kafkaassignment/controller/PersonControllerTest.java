package com.example.kafkaassignment.controller;

import com.example.kafkaassignment.enums.KafkaTopics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerTest {

    private PersonController personController;
    private KafkaTemplate<String, String> kafkaTemplateMock;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        kafkaTemplateMock = Mockito.mock(KafkaTemplate.class);
        personController = new PersonController(kafkaTemplateMock);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testSendMessage() throws Exception {
        String jsonPayload = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"dob\":\"1990-01-01\"}";

        // Perform POST request with mockMvc
        mockMvc.perform(post("/api/v1/send")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpect(status().isOk());

        // Verify that KafkaTemplate's send method was called with the right topic and message
        verify(kafkaTemplateMock, times(1)).send(
                Mockito.eq(KafkaTopics.CUSTOMER_INPUT.getTopicName()),
                Mockito.anyString());
    }
}
