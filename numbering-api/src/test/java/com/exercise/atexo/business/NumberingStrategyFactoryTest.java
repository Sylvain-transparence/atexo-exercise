package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberingStrategyFactoryTest {

    @Test
    @DisplayName("Should return LastNameStrategy for LAST_NAME")
    void shouldReturnLastNameStrategy() {
        NumberingStrategy strategy = NumberingStrategyFactory.getStrategy(CriterionType.LAST_NAME);
        assertThat(strategy).isInstanceOf(LastNameStrategy.class);
    }

    @Test
    @DisplayName("Should return FirstNameStrategy for FIRST_NAME")
    void shouldReturnFirstNameStrategy() {
        NumberingStrategy strategy = NumberingStrategyFactory.getStrategy(CriterionType.FIRST_NAME);
        assertThat(strategy).isInstanceOf(FirstNameStrategy.class);
    }

    @Test
    @DisplayName("Should return BirthYearStrategy for BIRTH_YEAR")
    void shouldReturnBirthYearStrategy() {
        NumberingStrategy strategy = NumberingStrategyFactory.getStrategy(CriterionType.BIRTH_YEAR);
        assertThat(strategy).isInstanceOf(BirthYearStrategy.class);
    }

    @Test
    @DisplayName("Should return CounterStrategy for COUNTER")
    void shouldReturnCounterStrategy() {
        NumberingStrategy strategy = NumberingStrategyFactory.getStrategy(CriterionType.COUNTER);
        assertThat(strategy).isInstanceOf(CounterStrategy.class);
    }
}
