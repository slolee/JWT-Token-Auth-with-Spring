package com.zn.iotproject.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUserException extends AuthenticationException {
    public InvalidUserException(String message) {
        super(message);
    }
}
