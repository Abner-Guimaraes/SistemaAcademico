package org.example.exception;

public class AuthorizationException extends SecuritySystemException {
    public AuthorizationException(String message) {
        super(message);
    }
}
