package com.SQD20.SQD20.LIVEPROJECT.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private Long id;
    private String responseCode;
    private String responseMessage;
    private String email;
    private String firstName;
    private String accessToken;
}
