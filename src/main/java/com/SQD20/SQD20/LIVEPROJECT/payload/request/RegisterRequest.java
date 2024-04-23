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
    @Size(min = 2, max = 125, message = "Firstname must be at least 2 characters long")
    @NotBlank(message = "Firstname must not be empty")
    private String firstName;

    @Size(min = 2, max = 125, message = "Lastname must be at least 2 characters long")
    @NotBlank(message = "Lastname must not be empty")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email
    private String email;

    @Size(min = 10, max = 20, message = "Phone number must be at least 10 characters long")
    @NotBlank(message = "Phone number must not be empty")
    private String phoneNumber;

    @Size(min = 6, max = 25, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password must not be empty")
    private String password;
}
