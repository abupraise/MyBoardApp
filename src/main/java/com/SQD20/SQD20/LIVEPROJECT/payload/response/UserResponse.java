package com.SQD20.SQD20.LIVEPROJECT.payload.response;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.TaskList;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String responseMessage;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}

