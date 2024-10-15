package com.exercise.atexo.mapper;

import com.exercise.atexo.domain.CriterionConfigurationDomain;
import com.exercise.atexo.domain.CriterionType;
import com.exercise.atexo.domain.NumberingConfigurationDomain;
import com.exercise.atexo.numbering.dto.CriterionConfigurationDto;
import com.exercise.atexo.numbering.dto.CriterionTypeDto;
import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class NumberingConfigurationMapperTest {

    @Test
    @DisplayName("toDomain should return null when dto is null")
    void toDomain_shouldReturnNull_whenDtoIsNull() {
        assertNull(NumberingConfigurationMapper.toDomain(null));
    }

    @Test
    @DisplayName("toDomain should map correctly when dto is valid")
    void toDomain_shouldMapCorrectly_whenDtoIsValid() {
        // Arrange
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(10L);

        List<CriterionConfigurationDto> criteriaDto = Arrays.asList(
                new CriterionConfigurationDto()
                        .order(1)
                        .type(CriterionTypeDto.LAST_NAME)
                        .prefix("L")
                        .suffix("-")
                        .length(3),
                new CriterionConfigurationDto()
                        .order(2)
                        .type(CriterionTypeDto.FIRST_NAME)
                        .prefix("F")
                        .suffix("_")
                        .length(2)
        );
        dto.setCriteriaConfigurations(criteriaDto);

        // Act
        NumberingConfigurationDomain result = NumberingConfigurationMapper.toDomain(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getInitialCounter()).isEqualTo(dto.getCounter());
        assertThat(result.getCounter()).isEqualTo(dto.getCounter());

        List<CriterionConfigurationDomain> resultCriteria = result.getCriteriaConfigurations();
        assertThat(resultCriteria).hasSize(2);

        // Check first criterion
        CriterionConfigurationDomain firstCriterion = resultCriteria.getFirst();
        assertThat(firstCriterion.getOrder()).isEqualTo(1);
        assertThat(firstCriterion.getCriterionType()).isEqualTo(CriterionType.LAST_NAME);
        assertThat(firstCriterion.getPrefix()).isEqualTo("L");
        assertThat(firstCriterion.getSuffix()).isEqualTo("-");
        assertThat(firstCriterion.getLength()).isEqualTo(3);

        // Check second criterion
        CriterionConfigurationDomain secondCriterion = resultCriteria.get(1);
        assertThat(secondCriterion.getOrder()).isEqualTo(2);
        assertThat(secondCriterion.getCriterionType()).isEqualTo(CriterionType.FIRST_NAME);
        assertThat(secondCriterion.getPrefix()).isEqualTo("F");
        assertThat(secondCriterion.getSuffix()).isEqualTo("_");
        assertThat(secondCriterion.getLength()).isEqualTo(2);
    }
}
