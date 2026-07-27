package com.taskflow.exception;

/**
 * Thrown when an authenticated user tries to access or modify a resource
 * they don't own. Mapped to HTTP 403 by GlobalExceptionHandler.
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
