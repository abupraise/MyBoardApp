package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;

public class EmailNotSentException extends RuntimeException{

    public EmailNotSentException(String message){
        super(message);
    }
}
