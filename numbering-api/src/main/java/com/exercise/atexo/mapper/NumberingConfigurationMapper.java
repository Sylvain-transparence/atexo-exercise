package com.exercise.atexo.mapper;

import com.exercise.atexo.domain.NumberingConfigurationDomain;
import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;

public final class NumberingConfigurationMapper {

    public static NumberingConfigurationDomain toDomain(NumberingConfigurationDto dto) {
        if (dto == null) {
            return null;
        }

        NumberingConfigurationDomain numberingConfiguration = new NumberingConfigurationDomain();
        numberingConfiguration.setInitialCounter(dto.getCounter());
        numberingConfiguration.setCriteriaConfigurations(CriteriaConfigurationMapper.toDomains(dto.getCriteriaConfigurations()));
        numberingConfiguration.setCounter(dto.getCounter());
        return numberingConfiguration;
    }

    public static NumberingConfigurationDto toDto(NumberingConfigurationDomain domain) {
        if (domain == null) {
            return null;
        }

        NumberingConfigurationDto numberingConfigurationDto = new NumberingConfigurationDto();
        numberingConfigurationDto.setCounter(domain.getInitialCounter());
        numberingConfigurationDto.setCurrentCounter(domain.getCounter());
        numberingConfigurationDto.setCriteriaConfigurations(CriteriaConfigurationMapper.toDtos(domain.getCriteriaConfigurations()));
        return numberingConfigurationDto;
    }
}
