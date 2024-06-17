package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bzu.hotel_management_system.request.AuthenticationRequest;
import com.bzu.hotel_management_system.response.AuthenticationResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest registerRequest);
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
