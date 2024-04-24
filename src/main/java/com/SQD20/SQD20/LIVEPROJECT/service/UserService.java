package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.AuthenticationRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.RegisterRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.UpdateUserRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.RegisterResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.AuthenticationResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UserService {
    public RegisterResponse register(RegisterRequest registerRequest) throws MessagingException, JsonProcessingException;
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public String verifyEmail(String token);
    public ResponseEntity<?> resendEmailVerification(String email);
    UserResponse editUser(Long id, UpdateUserRequest updateUserRequest);
    UserResponse viewUser(Long id);
    public String resetPassword(String email, String oldPassword, String newPassword);
    public  ResponseEntity<?> forgotPasswordEmail(String email);
    public String verifyForgotPasswordEmail(String token) throws IOException;
    String forgotPassword(String email, String newPassword, String confirmPassword);
    public UserDetails loadUserByUsername(String username);
}
