package com.exercise.atexo.service;

import com.exercise.atexo.business.NumberingStrategy;
import com.exercise.atexo.business.NumberingStrategyFactory;
import com.exercise.atexo.domain.RegisteredDomain;
import com.exercise.atexo.domain.CriterionConfigurationDomain;
import com.exercise.atexo.domain.CriterionType;
import com.exercise.atexo.domain.NumberingConfigurationDomain;
import com.exercise.atexo.exception.ConfigurationNotFoundException;
import com.exercise.atexo.exception.InvalidConfigurationException;
import com.exercise.atexo.exception.NumberGenerationException;
import com.exercise.atexo.mapper.NumberingConfigurationMapper;
import com.exercise.atexo.numbering.dto.CriterionConfigurationDto;
import com.exercise.atexo.numbering.dto.CriterionTypeDto;
import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberingServiceTest {

    @Mock
    private NumberingStrategy numberingStrategy;

    @InjectMocks
    private NumberingService numberingService;

    @Test
    @DisplayName("generate number should generate correctly Example 1")
    void generateNumber_shouldGenerateCorrectly_Example1() {
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(10L)
                .criteriaConfigurations(Arrays.asList(
                        createCriterionConfig(1, CriterionTypeDto.FIRST_NAME, "", "-", 3),
                        createCriterionConfig(2, CriterionTypeDto.LAST_NAME, "", "_", 4),
                        createCriterionConfig(3, CriterionTypeDto.BIRTH_YEAR, "", "", 4),
                        createCriterionConfig(4, CriterionTypeDto.COUNTER, "C", "", 5)
                ));

        NumberingConfigurationDomain domain = new NumberingConfigurationDomain();
        domain.setCounter(10L);
        domain.setCriteriaConfigurations(Arrays.asList(
                createCriterionDomain(1, CriterionType.FIRST_NAME, "", "-", 3),
                createCriterionDomain(2, CriterionType.LAST_NAME, "", "_", 4),
                createCriterionDomain(3, CriterionType.BIRTH_YEAR, "", "", 4),
                createCriterionDomain(4, CriterionType.COUNTER, "C", "", 5)
        ));

        try (var mockedStatic = mockStatic(NumberingConfigurationMapper.class)) {
            mockedStatic.when(() -> NumberingConfigurationMapper.toDomain(dto)).thenReturn(domain);

            numberingService.saveConfiguration(dto);
        }

        RegisteredDomain registered = new RegisteredDomain("Marc", "PASSAU", LocalDate.of(1974, 4, 24));

        String result = numberingService.generateNumber(registered);

        assertThat(result).isEqualTo("MAR-PASS_1974C00010");
    }

    @Test
    @DisplayName("generate number should generate correctly Example 2")
    void generateNumber_shouldGenerateCorrectly_Example2() {
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(7L)
                .criteriaConfigurations(Arrays.asList(
                        createCriterionConfig(2, CriterionTypeDto.FIRST_NAME, "", "-", 3),
                        createCriterionConfig(1, CriterionTypeDto.LAST_NAME, "", "_", 4),
                        createCriterionConfig(4, CriterionTypeDto.BIRTH_YEAR, "N", "", 4),
                        createCriterionConfig(3, CriterionTypeDto.COUNTER, "C", "", 5)
                ));

        numberingService.saveConfiguration(dto);

        RegisteredDomain registered = new RegisteredDomain("Isaac", "ANTOINE", LocalDate.of(1992, 4, 24));

        String result = numberingService.generateNumber(registered);

        assertThat(result).isEqualTo("ANTO_ISA-C00007N1992");
    }

    private CriterionConfigurationDto createCriterionConfig(int order, CriterionTypeDto type, String prefix, String suffix, int length) {
        return new CriterionConfigurationDto()
                .order(order)
                .type(type)
                .prefix(prefix)
                .suffix(suffix)
                .length(length);
    }

    private CriterionConfigurationDomain createCriterionDomain(int order, CriterionType type, String prefix, String suffix, int length) {
        CriterionConfigurationDomain config = new CriterionConfigurationDomain();
        config.setOrder(order);
        config.setCriterionType(type);
        config.setPrefix(prefix);
        config.setSuffix(suffix);
        config.setLength(length);
        return config;
    }

    @Test
    @DisplayName("save configuration should save configuration correctly")
    void saveConfiguration_shouldSaveConfigurationCorrectly() {
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L);

        CriterionConfigurationDto criterionDto = new CriterionConfigurationDto()
                .order(1)
                .type(CriterionTypeDto.LAST_NAME)
                .prefix("L")
                .suffix("-")
                .length(5);

        dto.addCriteriaConfigurationsItem(criterionDto);

        numberingService.saveConfiguration(dto);

        NumberingConfigurationDomain savedConfig = numberingService.getSavedConfiguration();
        assertThat(savedConfig).isNotNull();
        assertThat(savedConfig.getCounter()).isEqualTo(1L);
        assertThat(savedConfig.getCriteriaConfigurations()).hasSize(1);
        assertThat(savedConfig.getCriteriaConfigurations().getFirst().getCriterionType()).isEqualTo(CriterionType.LAST_NAME);
    }

    @Test
    @DisplayName("generate number should throw exception when configuration is null")
    void generateNumber_shouldThrowExceptionWhenConfigurationIsNull() {
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        assertThatThrownBy(() -> numberingService.generateNumber(registered))
                .isInstanceOf(ConfigurationNotFoundException.class)
                .hasMessage("No configuration has been saved");
    }

    @Test
    @DisplayName("generate number should generate number correctly")
    void generateNumber_shouldGenerateNumberCorrectly() {
        // Arrange
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L);

        CriterionConfigurationDto lastNameConfig = new CriterionConfigurationDto()
                .order(1)
                .type(CriterionTypeDto.LAST_NAME)
                .prefix("L")
                .suffix("-")
                .length(5);

        CriterionConfigurationDto counterConfig = new CriterionConfigurationDto()
                .order(2)
                .type(CriterionTypeDto.COUNTER)
                .prefix("C")
                .suffix("")
                .length(3);

        dto.addCriteriaConfigurationsItem(lastNameConfig);
        dto.addCriteriaConfigurationsItem(counterConfig);

        numberingService.saveConfiguration(dto);
        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = numberingService.generateNumber(registered);

        assertThat(result).isEqualTo("LDOE-C001");
        assertThat(numberingService.getSavedConfiguration().getCounter()).isEqualTo(2L);
    }

    @Test
    @DisplayName("generate number should handle multiple criteria")
    void generateNumber_shouldHandleMultipleCriteria() {
        // Arrange
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L);

        CriterionConfigurationDto lastNameConfig = new CriterionConfigurationDto()
                .order(1)
                .type(CriterionTypeDto.LAST_NAME)
                .prefix("")
                .suffix("")
                .length(3);

        CriterionConfigurationDto firstNameConfig = new CriterionConfigurationDto()
                .order(2)
                .type(CriterionTypeDto.FIRST_NAME)
                .prefix("")
                .suffix("")
                .length(3);

        CriterionConfigurationDto birthYearConfig = new CriterionConfigurationDto()
                .order(3)
                .type(CriterionTypeDto.BIRTH_YEAR)
                .prefix("")
                .suffix("")
                .length(4);

        dto.addCriteriaConfigurationsItem(lastNameConfig);
        dto.addCriteriaConfigurationsItem(firstNameConfig);
        dto.addCriteriaConfigurationsItem(birthYearConfig);

        numberingService.saveConfiguration(dto);

        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result = numberingService.generateNumber(registered);

        assertThat(result).isEqualTo("DOEJOH1990");
        assertThat(numberingService.getSavedConfiguration().getCounter()).isEqualTo(2L);
    }

    @Test
    @DisplayName("generate number should increment counter correctly")
    void generateNumber_shouldIncrementCounterCorrectly() {
        // Arrange
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L);

        CriterionConfigurationDto counterConfig = new CriterionConfigurationDto()
                .order(1)
                .type(CriterionTypeDto.COUNTER)
                .prefix("C")
                .suffix("")
                .length(3);

        dto.addCriteriaConfigurationsItem(counterConfig);

        numberingService.saveConfiguration(dto);

        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        String result1 = numberingService.generateNumber(registered);
        String result2 = numberingService.generateNumber(registered);

        assertThat(result1).isEqualTo("C001");
        assertThat(result2).isEqualTo("C002");
        assertThat(numberingService.getSavedConfiguration().getCounter()).isEqualTo(3L);
    }

    @Test
    @DisplayName("save configuration should throw invalid configuration exception when dto is null")
    void saveConfiguration_shouldThrowInvalidConfigurationException_whenDtoIsNull() {
        assertThatThrownBy(() -> numberingService.saveConfiguration(null))
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("Numbering configuration cannot be null");
    }

    @Test
    @DisplayName("save configuration should throw invalid configuration exception when criteria is empty")
    void saveConfiguration_shouldThrowInvalidConfigurationException_whenCriteriaIsEmpty() {
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L)
                .criteriaConfigurations(Collections.emptyList());

        assertThatThrownBy(() -> numberingService.saveConfiguration(dto))
                .isInstanceOf(InvalidConfigurationException.class)
                .hasMessage("Invalid numbering configuration");
    }

    @Test
    @DisplayName("generate number should throw number generation exception when strategy fails")
    void generateNumber_shouldThrowNumberGenerationException_whenStrategyFails() {
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L)
                .criteriaConfigurations(Collections.singletonList(
                        createCriterionConfig(1, CriterionTypeDto.LAST_NAME, "", "", 3)
                ));

        NumberingConfigurationDomain domain = new NumberingConfigurationDomain();
        domain.setCounter(1L);
        domain.setCriteriaConfigurations(List.of(
                createCriterionDomain(1, CriterionType.LAST_NAME, "", "", 3)
        ));

        try (var mockedStatic = mockStatic(NumberingConfigurationMapper.class)) {
            mockedStatic.when(() -> NumberingConfigurationMapper.toDomain(dto)).thenReturn(domain);
            numberingService.saveConfiguration(dto);
        }

        RegisteredDomain registered = new RegisteredDomain("John", "Doe", LocalDate.of(1990, 1, 1));

        try (var mockedStatic = mockStatic(NumberingStrategyFactory.class)) {
            mockedStatic.when(() -> NumberingStrategyFactory.getStrategy(any())).thenReturn(numberingStrategy);
            when(numberingStrategy.generate(any(), any(), any())).thenThrow(new RuntimeException("Strategy failed"));

            assertThatThrownBy(() -> numberingService.generateNumber(registered))
                    .isInstanceOf(NumberGenerationException.class)
                    .hasMessageContaining("Error occurred while generating number");
        }
    }
}
