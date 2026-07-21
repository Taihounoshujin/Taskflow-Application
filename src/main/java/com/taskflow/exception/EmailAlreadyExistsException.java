package com.taskflow.exception;

/**
 * Thrown when trying to register a user with an email that already exists.
 * Mapped to HTTP 409 by GlobalExceptionHandler.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}