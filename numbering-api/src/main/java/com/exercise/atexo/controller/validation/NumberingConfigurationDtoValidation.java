package com.exercise.atexo.controller.validation;

import com.exercise.atexo.numbering.dto.CriterionConfigurationDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface NumberingConfigurationDtoValidation {
    @NotNull(message = "Counter cannot be null")
    @Min(value = 0, message = "Counter must be a positive number")
    Long getCounter();

    @NotEmpty(message = "Criteria configurations list cannot be empty")
    @Size(max = 4, message = "Maximum number of criteria is 4")
    List<CriterionConfigurationDto> getCriteriaConfigurations();
}
