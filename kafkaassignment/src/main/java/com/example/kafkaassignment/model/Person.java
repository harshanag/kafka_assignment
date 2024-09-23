package com.example.kafkaassignment.model;

import java.time.LocalDate;

public record Person(Integer id, String firstName, String lastName, LocalDate dateOfBirth) {
}
