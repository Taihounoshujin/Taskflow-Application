package com.taskflow.exception;

// Mapped to HTTP 404 by GlobalExceptionHandler.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}