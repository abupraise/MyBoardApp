package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;


import com.SQD20.SQD20.LIVEPROJECT.domain.entites.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ErrorDetails> emailNotSentHandler(final EmailNotSentException ex){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .debugMessage("Email Not Sent")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
