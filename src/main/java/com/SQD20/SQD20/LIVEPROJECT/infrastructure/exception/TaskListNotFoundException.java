package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;

public class TaskListNotFoundException extends RuntimeException{
    public TaskListNotFoundException(String message) {
        super(message);
    }
}
