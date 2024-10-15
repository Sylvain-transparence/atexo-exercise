package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LastNameStrategyTest {

    private final NumberingStrategy strategy = new LastNameStrategy();

    @Test
    @DisplayName("LastNameStrategy should generate correct string")
    void shouldGenerateCorrectString() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 5);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE-DOE-SUF");
    }

    @Test
    @DisplayName("LastNameStrategy should truncate long last names")
    void shouldTruncateLongLastNames() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 3);
        RegisteredDomain registered = new RegisteredDomain("John", "Johnson", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE-JOH-SUF");
    }

    @Test
    @DisplayName("LastNameStrategy should handle empty last name")
    void shouldHandleEmptyLastName() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 5);
        RegisteredDomain registered = new RegisteredDomain("John", "", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE--SUF");
    }
}
