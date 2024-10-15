package com.exercise.atexo.mapper;

import com.exercise.atexo.domain.CriterionConfigurationDomain;
import com.exercise.atexo.domain.CriterionType;
import com.exercise.atexo.numbering.dto.CriterionConfigurationDto;
import com.exercise.atexo.numbering.dto.CriterionTypeDto;

import java.util.List;

public final class CriteriaConfigurationMapper {

    public static List<CriterionConfigurationDomain> toDomains(List<CriterionConfigurationDto> dtos) {
        return dtos.stream().map(CriteriaConfigurationMapper::toDomain).toList();
    }

    public static CriterionConfigurationDomain toDomain(CriterionConfigurationDto dto) {
        if (dto == null) {
            return null;
        }

        CriterionConfigurationDomain criteriaConfiguration = new CriterionConfigurationDomain();
        criteriaConfiguration.setOrder(dto.getOrder());
        criteriaConfiguration.setPrefix(dto.getPrefix());
        criteriaConfiguration.setSuffix(dto.getSuffix());
        criteriaConfiguration.setLength(dto.getLength());
        criteriaConfiguration.setCriterionType(CriterionType.valueOf(dto.getType().name()));
        return criteriaConfiguration;
    }


    public static CriterionConfigurationDto toDto(CriterionConfigurationDomain entity) {
        if (entity == null) {
            return null;
        }

        CriterionConfigurationDto criteriaConfigurationDto = new CriterionConfigurationDto();
        criteriaConfigurationDto.setOrder(entity.getOrder());
        criteriaConfigurationDto.setPrefix(entity.getPrefix());
        criteriaConfigurationDto.setSuffix(entity.getSuffix());
        criteriaConfigurationDto.setLength(entity.getLength());
        criteriaConfigurationDto.setType(CriterionTypeDto.fromValue(entity.getCriterionType().name()));
        return criteriaConfigurationDto;
    }


    public static List<CriterionConfigurationDto> toDtos(List<CriterionConfigurationDomain> entity) {
        if (entity == null) {
            return null;
        }

        return entity.stream().map(CriteriaConfigurationMapper::toDto).toList();
    }
}
