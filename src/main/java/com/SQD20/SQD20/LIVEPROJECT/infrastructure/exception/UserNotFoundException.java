package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
