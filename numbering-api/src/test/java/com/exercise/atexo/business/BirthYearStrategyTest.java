package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BirthYearStrategyTest {

    private final NumberingStrategy strategy = new BirthYearStrategy();

    @Test
    @DisplayName("BirthYearStrategy should generate correct string")
    void shouldGenerateCorrectString() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 4);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE-1990-SUF");
    }

    @Test
    @DisplayName("BirthYearStrategy should handle different years")
    void shouldHandleDifferentYears() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 4);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(2000, 12, 31));

        String result = strategy.generate(criterion, registered, 1L);

        assertThat(result).isEqualTo("PRE-2000-SUF");
    }

    @Test
    @DisplayName("BirthYearStrategy should handle null birth date")
    void shouldHandleNullBirthDate() {
        CriterionDomain criterion = new CriterionDomain("PRE-", "-SUF", 4);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", null);

        assertThatThrownBy(() -> strategy.generate(criterion, registered, 1L))
                .isInstanceOf(NullPointerException.class);
    }
}
