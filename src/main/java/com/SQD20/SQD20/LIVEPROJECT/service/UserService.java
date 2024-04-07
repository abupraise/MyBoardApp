package com.SQD20.SQD20.LIVEPROJECT.service;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.AuthenticationRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.RegisterRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.AuthenticationResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public AuthenticationResponse register(RegisterRequest registerRequest);
    public LoginResponse authenticate(AuthenticationRequest request);
}
