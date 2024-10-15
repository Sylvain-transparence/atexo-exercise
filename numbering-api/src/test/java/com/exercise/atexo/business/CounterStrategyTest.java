package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CounterStrategyTest {

    private final NumberingStrategy strategy = new CounterStrategy();

    @Test
    @DisplayName("CounterStrategy should generate correct string")
    void shouldGenerateCorrectString() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 3);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 42L);

        assertThat(result).isEqualTo("PRE-042-SUF");
    }

    @Test
    @DisplayName("CounterStrategy should pad with zeros")
    void shouldPadWithZeros() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 5);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 7L);

        assertThat(result).isEqualTo("PRE-00007-SUF");
    }

    @Test
    @DisplayName("CounterStrategy should handle large numbers")
    void shouldHandleLargeNumbers() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 3);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1000L);

        assertThat(result).isEqualTo("PRE-1000-SUF");
    }
}
