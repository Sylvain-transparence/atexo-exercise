package com.exercise.atexo.controller.advice;

import com.exercise.atexo.exception.ConfigurationNotFoundException;
import com.exercise.atexo.exception.InvalidConfigurationException;
import com.exercise.atexo.exception.NumberGenerationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(ConfigurationNotFoundException.class)
    public ResponseEntity<String> handleConfigurationNotFoundException(ConfigurationNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidConfigurationException.class)
    public ResponseEntity<String> handleInvalidConfigurationException(InvalidConfigurationException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberGenerationException.class)
    public ResponseEntity<String> handleNumberGenerationException(NumberGenerationException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
