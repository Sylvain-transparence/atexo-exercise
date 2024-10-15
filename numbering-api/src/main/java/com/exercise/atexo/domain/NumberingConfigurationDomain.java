package com.exercise.atexo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class NumberingConfigurationDomain {

    private Long initialCounter;
    private List<CriterionConfigurationDomain> criteriaConfigurations = new ArrayList<>();
    private Long counter;
}
