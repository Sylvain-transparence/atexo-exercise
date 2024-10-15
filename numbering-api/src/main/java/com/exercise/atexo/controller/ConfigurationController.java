package com.exercise.atexo.controller;

import com.exercise.atexo.mapper.NumberingConfigurationMapper;
import com.exercise.atexo.numbering.api.ConfigurationApi;
import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;
import com.exercise.atexo.service.NumberingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ConfigurationController implements ConfigurationApi {

    private final NumberingService numberingService;

    @Override
    public void configureNumbering(NumberingConfigurationDto numberingConfigurationDto) {
        numberingService.saveConfiguration(numberingConfigurationDto);
    }

    @Override
    public NumberingConfigurationDto getCurrentConfiguration() {
        return NumberingConfigurationMapper.toDto(numberingService.getSavedConfiguration());
    }
}
