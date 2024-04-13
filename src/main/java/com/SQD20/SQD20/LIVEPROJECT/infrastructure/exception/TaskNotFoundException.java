package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
