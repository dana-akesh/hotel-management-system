package com.bzu.hotel_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private long fieldValue;
    private String stringValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String stringValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, stringValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.stringValue = stringValue;
    }

}