package com.SQD20.SQD20.LIVEPROJECT.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    @NotBlank(message = "Email must not be empty")
    @Email
    private String email;
    private String phoneNumber;
    @Size(min = 6, max = 16, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password must not be empty")
    private String password;
}
