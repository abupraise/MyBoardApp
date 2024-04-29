package com.SQD20.SQD20.LIVEPROJECT.payload.response;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.ErrorDetails;
import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserResponse<T> {
    private String responseMessage;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private T data;

    public UserResponse(String responseMessage, String firstName, String lastName, String email, String phoneNumber, T data) {
        this.responseMessage = responseMessage;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.data = data;
    }

    public UserResponse(String responseMessage, ErrorDetails errorDetails) {
        this.responseMessage = responseMessage;
    }

    public UserResponse(String message, String fileUrl) {
        this.responseMessage = message;
        this.data = (T) fileUrl;
    }

    public UserResponse(String message) {
        this.responseMessage = message;
    }

    public String getData() {
        return data != null ? data.toString() : null;
    }
}

