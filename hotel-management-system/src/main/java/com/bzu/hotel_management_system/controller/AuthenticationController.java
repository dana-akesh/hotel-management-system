package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.request.AuthenticationRequest;
import com.bzu.hotel_management_system.request.RegisterRequest;
import com.bzu.hotel_management_system.response.AuthenticationResponse;
import com.bzu.hotel_management_system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // register
    @Operation(
            summary = "Register",
            description = "Register a new user",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
            }
    )

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    // authenticate
    @Operation(
            summary = "Authenticate",
            description = "Authenticate a user",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
            }
    )

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    // refresh token
    @Operation(
            summary = "Refresh token",
            description = "Refresh",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
            }
    )
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
