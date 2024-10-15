package com.exercise.atexo.mapper;

import com.exercise.atexo.domain.CriterionConfigurationDomain;
import com.exercise.atexo.domain.CriterionType;
import com.exercise.atexo.numbering.dto.CriterionConfigurationDto;
import com.exercise.atexo.numbering.dto.CriterionTypeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CriteriaConfigurationMapperTest {

    @Test
    @DisplayName("toDomains should correctly map a list of DTOs to a list of domains")
    void toDomainsShouldMapDtosToDomainsCorrectly() {
        // Arrange
        CriterionConfigurationDto dto1 = new CriterionConfigurationDto();
        dto1.setOrder(1);
        dto1.setPrefix("PRE");
        dto1.setSuffix("SUF");
        dto1.setLength(5);
        dto1.setType(CriterionTypeDto.LAST_NAME);

        CriterionConfigurationDto dto2 = new CriterionConfigurationDto();
        dto2.setOrder(2);
        dto2.setPrefix("ABC");
        dto2.setSuffix("XYZ");
        dto2.setLength(3);
        dto2.setType(CriterionTypeDto.FIRST_NAME);

        List<CriterionConfigurationDto> dtos = Arrays.asList(dto1, dto2);

        List<CriterionConfigurationDomain> result = CriteriaConfigurationMapper.toDomains(dtos);

        assertThat(result).hasSize(2);
        assertThat(result.get(0))
                .extracting("order", "prefix", "suffix", "length", "criterionType")
                .containsExactly(1, "PRE", "SUF", 5, CriterionType.LAST_NAME);
        assertThat(result.get(1))
                .extracting("order", "prefix", "suffix", "length", "criterionType")
                .containsExactly(2, "ABC", "XYZ", 3, CriterionType.FIRST_NAME);
    }

    @Test
    @DisplayName("toDomain should correctly map a DTO to a domain")
    void toDomainShouldMapDtoToDomainCorrectly() {
        CriterionConfigurationDto dto = new CriterionConfigurationDto();
        dto.setOrder(1);
        dto.setPrefix("PRE");
        dto.setSuffix("SUF");
        dto.setLength(5);
        dto.setType(CriterionTypeDto.BIRTH_YEAR);

        CriterionConfigurationDomain result = CriteriaConfigurationMapper.toDomain(dto);

        assertThat(result)
                .extracting("order", "prefix", "suffix", "length", "criterionType")
                .containsExactly(1, "PRE", "SUF", 5, CriterionType.BIRTH_YEAR);
    }

    @Test
    @DisplayName("toDomain should return null when dto is null")
    void toDomainShouldReturnNullWhenDtoIsNull() {
        // Act
        CriterionConfigurationDomain result = CriteriaConfigurationMapper.toDomain(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("toDto should correctly map a domain to a DTO")
    void toDtoShouldMapDomainToDtoCorrectly() {
        // Arrange
        CriterionConfigurationDomain domain = new CriterionConfigurationDomain();
        domain.setOrder(1);
        domain.setPrefix("PRE");
        domain.setSuffix("SUF");
        domain.setLength(5);
        domain.setCriterionType(CriterionType.COUNTER);

        // Act
        CriterionConfigurationDto result = CriteriaConfigurationMapper.toDto(domain);

        // Assert
        assertThat(result)
                .extracting("order", "prefix", "suffix", "length", "type")
                .containsExactly(1, "PRE", "SUF", 5, CriterionTypeDto.COUNTER);
    }

    @Test
    @DisplayName("toDto should return null when dto is null")
    void toDtoShouldReturnNullWhenDtoIsNull() {
        // Act
        CriterionConfigurationDto result = CriteriaConfigurationMapper.toDto(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("toDtos should correctly map a list of domains to a list of DTOs")
    void toDtosShouldMapDomainsToDtosCorrectly() {
        // Arrange
        CriterionConfigurationDomain domain1 = new CriterionConfigurationDomain();
        domain1.setOrder(1);
        domain1.setPrefix("PRE");
        domain1.setSuffix("SUF");
        domain1.setLength(5);
        domain1.setCriterionType(CriterionType.LAST_NAME);

        CriterionConfigurationDomain domain2 = new CriterionConfigurationDomain();
        domain2.setOrder(2);
        domain2.setPrefix("ABC");
        domain2.setSuffix("XYZ");
        domain2.setLength(3);
        domain2.setCriterionType(CriterionType.FIRST_NAME);

        List<CriterionConfigurationDomain> domains = Arrays.asList(domain1, domain2);

        // Act
        List<CriterionConfigurationDto> result = CriteriaConfigurationMapper.toDtos(domains);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0))
                .extracting("order", "prefix", "suffix", "length", "type")
                .containsExactly(1, "PRE", "SUF", 5, CriterionTypeDto.LAST_NAME);
        assertThat(result.get(1))
                .extracting("order", "prefix", "suffix", "length", "type")
                .containsExactly(2, "ABC", "XYZ", 3, CriterionTypeDto.FIRST_NAME);
    }

    @Test
    @DisplayName("toDtos should return null when dto is null")
    void toDtosShouldReturnNullWhenDtoIsNull() {
        // Act
        List<CriterionConfigurationDto> result = CriteriaConfigurationMapper.toDtos(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("toDomain should correctly map all CriterionTypeDto values")
    void toDomainShouldMapAllCriterionTypeDtoValues() {
        for (CriterionTypeDto dtoType : CriterionTypeDto.values()) {
            // Arrange
            CriterionConfigurationDto dto = new CriterionConfigurationDto();
            dto.setType(dtoType);

            // Act
            CriterionConfigurationDomain result = CriteriaConfigurationMapper.toDomain(dto);

            // Assert
            assertThat(result.getCriterionType().name()).isEqualTo(dtoType.name());
        }
    }

    @Test
    @DisplayName("toDto should correctly map all CriterionType values")
    void toDtoShouldMapAllCriterionTypeValues() {
        for (CriterionType domainType : CriterionType.values()) {
            // Arrange
            CriterionConfigurationDomain domain = new CriterionConfigurationDomain();
            domain.setCriterionType(domainType);

            // Act
            CriterionConfigurationDto result = CriteriaConfigurationMapper.toDto(domain);

            // Assert
            assertThat(result.getType().name()).isEqualTo(domainType.name());
        }
    }
}
