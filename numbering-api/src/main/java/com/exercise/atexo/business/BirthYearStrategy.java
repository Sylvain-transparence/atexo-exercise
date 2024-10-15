package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;

public class BirthYearStrategy implements NumberingStrategy {

    @Override
    public String generate(CriterionDomain criterion, RegisteredDomain registered, Long counter) {
        return criterion.prefix() + registered.birthDate().getYear() + criterion.suffix();
    }
}
