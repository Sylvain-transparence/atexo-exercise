package com.exercise.atexo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CriterionConfigurationDomain {
    private Integer order;
    private CriterionType criterionType;
    private String prefix;
    private String suffix;
    private Integer length;
}
