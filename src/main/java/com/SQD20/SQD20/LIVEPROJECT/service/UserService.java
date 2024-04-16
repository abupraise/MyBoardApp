package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.AuthenticationRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.RegisterRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.RegisterResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.AuthenticationResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public RegisterResponse register(RegisterRequest registerRequest);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public String verifyEmail(String token);
    public ResponseEntity<?> resendEmailVerification(String email);
    UserResponse editUser(Long id, RegisterRequest registerRequest);
    UserResponse getUserById(Long id);

}
