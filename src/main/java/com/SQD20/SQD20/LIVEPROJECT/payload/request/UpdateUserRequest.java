package com.SQD20.SQD20.LIVEPROJECT.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(min = 2, max = 125, message = "Firstname must be at least 2 characters long")
    @NotBlank(message = "Firstname must not be empty")
    private String firstName;

    @Size(min = 2, max = 125, message = "Lastname must be at least 2 characters long")
    @NotBlank(message = "Lastname must not be empty")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email
    private String email;

    @Size(min = 10, max = 20, message = "Firstname must be at least 2 characters long")
    @NotBlank(message = "Firstname must not be empty")
    private String phoneNumber;
}
