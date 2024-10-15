package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;

public class LastNameStrategy implements NumberingStrategy {

    @Override
    public String generate(CriterionDomain criterion, RegisteredDomain registered, Long counter) {
        return criterion.prefix() + formatString(registered.lastName().toUpperCase(), criterion.length()) + criterion.suffix();
    }

    private String formatString(String value, int length) {
        return value.length() > length ? value.substring(0, length) : value;
    }
}
