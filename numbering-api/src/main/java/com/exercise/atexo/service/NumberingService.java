package com.exercise.atexo.service;

import com.exercise.atexo.business.NumberingStrategy;
import com.exercise.atexo.business.NumberingStrategyFactory;
import com.exercise.atexo.domain.CriterionDomain;
import com.exercise.atexo.domain.RegisteredDomain;
import com.exercise.atexo.domain.CriterionConfigurationDomain;
import com.exercise.atexo.domain.NumberingConfigurationDomain;
import com.exercise.atexo.exception.ConfigurationNotFoundException;
import com.exercise.atexo.exception.InvalidConfigurationException;
import com.exercise.atexo.exception.NumberGenerationException;
import com.exercise.atexo.mapper.NumberingConfigurationMapper;
import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public final class NumberingService {

    private NumberingConfigurationDomain configurationDomain;

    public void saveConfiguration(NumberingConfigurationDto numberingConfiguration) {
        if (numberingConfiguration == null) {
            throw new InvalidConfigurationException("Numbering configuration cannot be null");
        }
        configurationDomain = NumberingConfigurationMapper.toDomain(numberingConfiguration);
        if (configurationDomain == null || configurationDomain.getCriteriaConfigurations() == null || configurationDomain.getCriteriaConfigurations().isEmpty()) {
            throw new InvalidConfigurationException("Invalid numbering configuration");
        }
    }

    public NumberingConfigurationDomain getSavedConfiguration() {
        if (configurationDomain == null) {
            throw new ConfigurationNotFoundException("No configuration has been saved");
        }
        return configurationDomain;
    }

    public String generateNumber(RegisteredDomain registered) {
        if (configurationDomain == null) {
            throw new ConfigurationNotFoundException("No configuration has been saved");
        }
        if (registered == null) {
            throw new IllegalArgumentException("Registered user cannot be null");
        }

        StringBuilder number = new StringBuilder();
        List<CriterionConfigurationDomain> criteria = configurationDomain.getCriteriaConfigurations();
        criteria = criteria.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(CriterionConfigurationDomain::getOrder))
                .toList();

        try {
            for (CriterionConfigurationDomain criterionConfiguration : criteria) {
                NumberingStrategy strategy = NumberingStrategyFactory.getStrategy(criterionConfiguration.getCriterionType());
                CriterionDomain criterion = new CriterionDomain(criterionConfiguration.getPrefix(), criterionConfiguration.getSuffix(), criterionConfiguration.getLength());
                String part = strategy.generate(criterion, registered, configurationDomain.getCounter());
                number.append(part);
            }
            configurationDomain.setCounter(configurationDomain.getCounter() + 1);
            return number.toString();
        } catch (Exception e) {
            throw new NumberGenerationException("Error occurred while generating number", e);
        }
    }
}
