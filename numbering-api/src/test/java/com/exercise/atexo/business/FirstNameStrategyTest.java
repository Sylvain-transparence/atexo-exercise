package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FirstNameStrategyTest {

    private final NumberingStrategy strategy = new FirstNameStrategy();

    @Test
    @DisplayName("FirstNameStrategy should generate correct string")
    void shouldGenerateCorrectString() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 5);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE-JOHN-SUF");
    }

    @Test
    @DisplayName("FirstNameStrategy should truncate long first names")
    void shouldTruncateLongFirstNames() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 3);
        RegisteredDomain registered = new RegisteredDomain("Elizabeth", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE-ELI-SUF");
    }

    @Test
    @DisplayName("FirstNameStrategy should handle empty first name")
    void shouldHandleEmptyFirstName() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 5);
        RegisteredDomain registered = new RegisteredDomain("", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE--SUF");
    }
}
