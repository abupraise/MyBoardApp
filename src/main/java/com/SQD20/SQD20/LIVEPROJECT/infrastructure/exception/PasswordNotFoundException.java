package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;

public class PasswordNotFoundException extends RuntimeException {
    public PasswordNotFoundException(String message) {
        super(message);
    }
}
