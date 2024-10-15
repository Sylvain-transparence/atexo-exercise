package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionType;

public class NumberingStrategyFactory {

    public static NumberingStrategy getStrategy(CriterionType criterionType) {
        return switch (criterionType) {
            case LAST_NAME -> new LastNameStrategy();
            case FIRST_NAME -> new FirstNameStrategy();
            case BIRTH_YEAR -> new BirthYearStrategy();
            case COUNTER -> new CounterStrategy();
        };
    }
}
