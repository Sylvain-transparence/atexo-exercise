package com.exercise.atexo.controller;

import com.exercise.atexo.domain.RegisteredDomain;
import com.exercise.atexo.numbering.dto.RegisteredDto;
import com.exercise.atexo.service.NumberingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NumberingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NumberingService numberingService;

    @InjectMocks
    private NumberingController numberingController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(numberingController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Pour gérer LocalDate
    }

    @Test
    void generateUniqueNumber_shouldReturnGeneratedNumber() throws Exception {
        RegisteredDto dto = new RegisteredDto()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1));

        when(numberingService.generateNumber(any(RegisteredDomain.class))).thenReturn("UNIQ123");

        mockMvc.perform(post("/api/numbering/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("UNIQ123"));
    }

    @Test
    void generateUniqueNumber_withConfigurationId_shouldReturnGeneratedNumber() throws Exception {
        RegisteredDto dto = new RegisteredDto()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1));

        when(numberingService.generateNumber(any(RegisteredDomain.class))).thenReturn("UNIQ456");

        mockMvc.perform(post("/api/numbering/generate")
                        .param("configurationId", "config1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("UNIQ456"));
    }
}
