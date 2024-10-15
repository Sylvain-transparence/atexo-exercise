package com.exercise.atexo.domain;

import java.time.LocalDate;

public record RegisteredDomain(String firstName, String lastName, LocalDate birthDate) {
}
