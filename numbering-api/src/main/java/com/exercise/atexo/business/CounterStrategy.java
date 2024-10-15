package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;

public class CounterStrategy implements NumberingStrategy {

    @Override
    public String generate(CriterionDomain criterion, RegisteredDomain registered, Long counter) {
        return criterion.prefix() + formatCounter(counter, criterion.length()) + criterion.suffix();
    }

    private String formatCounter(Long counter, int length) {
        return String.format("%0" + length + "d", counter);
    }
}
