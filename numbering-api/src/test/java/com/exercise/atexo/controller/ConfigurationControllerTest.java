package com.exercise.atexo.controller;

import com.exercise.atexo.numbering.dto.NumberingConfigurationDto;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ConfigurationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NumberingService numberingService;

    @InjectMocks
    private ConfigurationController configurationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(configurationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void configureNumbering_shouldCallServiceAndReturnOk() throws Exception {
        NumberingConfigurationDto dto = new NumberingConfigurationDto()
                .counter(1L);

        mockMvc.perform(post("/api/numbering/configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(numberingService).saveConfiguration(dto);
    }
}
