package com.bzu.hotel_management_system.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("path", "/api/v1/auth/register"); // or the relevant path

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return new ResponseEntity<>(errorResponse, headers, HttpStatus.CONFLICT);
    }

}
