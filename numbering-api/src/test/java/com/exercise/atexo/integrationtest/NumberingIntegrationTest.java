package com.exercise.atexo.integrationtest;

import com.exercise.atexo.AtexoApplication;
import com.exercise.atexo.numbering.dto.CriterionConfigurationDto;
import com.exercise.atexo.numbering.dto.CriterionTypeDto;
import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;
import com.exercise.atexo.numbering.dto.RegisteredDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AtexoApplication.class)
@AutoConfigureMockMvc
class NumberingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testConfigureAndGenerateNumber() throws Exception {
        // Configure numbering
        NumberingConfigurationDto configDto = new NumberingConfigurationDto()
                .counter(1L)
                .criteriaConfigurations(Arrays.asList(
                        new CriterionConfigurationDto()
                                .order(1)
                                .type(CriterionTypeDto.LAST_NAME)
                                .prefix("L")
                                .suffix("-")
                                .length(3),
                        new CriterionConfigurationDto()
                                .order(2)
                                .type(CriterionTypeDto.COUNTER)
                                .prefix("C")
                                .suffix("")
                                .length(3)
                ));

        mockMvc.perform(post("/api/numbering/configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configDto)))
                .andExpect(status().isOk());

        // Generate number
        RegisteredDto registeredDto = new RegisteredDto()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1));

        mockMvc.perform(post("/api/numbering/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registeredDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("LDOE-C001"));
    }

    @Test
    void testConfigureAndGenerateNumber_Example1() throws Exception {
        NumberingConfigurationDto configDto = new NumberingConfigurationDto()
                .counter(10L)
                .criteriaConfigurations(Arrays.asList(
                        createCriterionConfig(1, CriterionTypeDto.FIRST_NAME, "", "-", 3),
                        createCriterionConfig(2, CriterionTypeDto.LAST_NAME, "", "_", 4),
                        createCriterionConfig(3, CriterionTypeDto.BIRTH_YEAR, "", "", 4),
                        createCriterionConfig(4, CriterionTypeDto.COUNTER, "C", "", 5)
                ));

        mockMvc.perform(post("/api/numbering/configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configDto)))
                .andExpect(status().isOk());

        RegisteredDto registeredDto = new RegisteredDto()
                .firstName("Marc")
                .lastName("PASSAU")
                .birthDate(LocalDate.of(1974, 4, 24));

        mockMvc.perform(post("/api/numbering/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registeredDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("MAR-PASS_1974C00010"));
    }

    @Test
    void testConfigureAndGenerateNumber_Example2() throws Exception {
        NumberingConfigurationDto configDto = new NumberingConfigurationDto()
                .counter(7L)
                .criteriaConfigurations(Arrays.asList(
                        createCriterionConfig(2, CriterionTypeDto.FIRST_NAME, "", "-", 3),
                        createCriterionConfig(1, CriterionTypeDto.LAST_NAME, "", "_", 4),
                        createCriterionConfig(4, CriterionTypeDto.BIRTH_YEAR, "N", "", 4),
                        createCriterionConfig(3, CriterionTypeDto.COUNTER, "C", "", 5)
                ));

        mockMvc.perform(post("/api/numbering/configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configDto)))
                .andExpect(status().isOk());

        RegisteredDto registeredDto = new RegisteredDto()
                .firstName("Isaac")
                .lastName("ANTOINE")
                .birthDate(LocalDate.of(1992, 4, 24));

        mockMvc.perform(post("/api/numbering/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registeredDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("ANTO_ISA-C00007N1992"));
    }

    private CriterionConfigurationDto createCriterionConfig(int order, CriterionTypeDto type, String prefix, String suffix, int length) {
        return new CriterionConfigurationDto()
                .order(order)
                .type(type)
                .prefix(prefix)
                .suffix(suffix)
                .length(length);
    }
}
