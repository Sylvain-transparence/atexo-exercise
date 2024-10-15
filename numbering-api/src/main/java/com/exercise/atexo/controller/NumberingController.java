package com.exercise.atexo.controller;

import com.exercise.atexo.domain.RegisteredDomain;
import com.exercise.atexo.numbering.api.NumberingApi;
import com.exercise.atexo.numbering.dto.RegisteredDto;
import com.exercise.atexo.service.NumberingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class NumberingController implements NumberingApi {

    private final NumberingService numberingService;

    @Override
    public String generateUniqueNumber(RegisteredDto registeredDto) {
        RegisteredDomain registered = new RegisteredDomain(registeredDto.getFirstName(), registeredDto.getLastName(), registeredDto.getBirthDate());
        return numberingService.generateNumber(registered);
    }
}
