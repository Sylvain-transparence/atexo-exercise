package com.exercise.atexo.business;

import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;

public interface NumberingStrategy {
    String generate(CriterionDomain criterion, RegisteredDomain registered, Long counter);
}
