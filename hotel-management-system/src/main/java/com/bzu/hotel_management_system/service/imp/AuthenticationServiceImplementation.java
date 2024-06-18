package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.config.JwtService;
import com.bzu.hotel_management_system.entity.*;
import com.bzu.hotel_management_system.exception.UsernameAlreadyExistsException;
import com.bzu.hotel_management_system.repository.CustomerRepository;
import com.bzu.hotel_management_system.repository.EmployeeRepository;
import com.bzu.hotel_management_system.repository.TokenRepository;
import com.bzu.hotel_management_system.repository.UserRepository;
import com.bzu.hotel_management_system.request.AuthenticationRequest;
import com.bzu.hotel_management_system.request.RegisterRequest;
import com.bzu.hotel_management_system.response.AuthenticationResponse;
import com.bzu.hotel_management_system.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImplementation.class);

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImplementation(UserRepository userRepository, TokenRepository tokenRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Role role = registerRequest.getRole();  // Add this line to log the role
        logger.debug("Registering user with role: {}", role);  // Log the role

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists: " + registerRequest.getUsername());
        }

        var user = User.builder()
                .username(registerRequest.getUsername())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
        User savedUser;

        if (role == Role.CUSTOMER) {
            Customer customer = new Customer();
            customer.setUsername(user.getUsername());
            customer.setName(user.getName());
            customer.setPassword(user.getPassword());
            customer.setRole(user.getRole());
            savedUser = customerRepository.save(customer);
        } else {
            User user2 = new User();
            user2.setUsername(user.getUsername());
            user2.setName(user.getName());
            user2.setPassword(user.getPassword());
            user2.setRole(user.getRole());
            savedUser = userRepository.save(user2);
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        logger.debug("authenticate user 2");  // Log the role
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
