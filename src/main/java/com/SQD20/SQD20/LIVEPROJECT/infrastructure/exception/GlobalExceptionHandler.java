package com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception;


import com.SQD20.SQD20.LIVEPROJECT.domain.entites.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ErrorDetails> emailNotSentHandler(final EmailNotSentException ex){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .debugMessage("Email Not Sent")
                .dateTime(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDetails>taskNotFoundHandler(final TaskNotFoundException ex){
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .message(ex.getMessage())
                    .debugMessage("Task Not Found")
                    .dateTime(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND)
                    .build();
         return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND) ;
    }
    @ExceptionHandler(TaskListNotFoundException.class)
    public ResponseEntity<ErrorDetails>taskListNotFoundHandler(final TaskListNotFoundException ex){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .debugMessage("Task List Not Found")
                .dateTime(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return  ResponseEntity.ok(errorDetails);
    }
}
