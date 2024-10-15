package com.exercise.atexo.exception;

public class NumberGenerationException extends RuntimeException {
    public NumberGenerationException(String message) {
        super(message);
    }

    public NumberGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
