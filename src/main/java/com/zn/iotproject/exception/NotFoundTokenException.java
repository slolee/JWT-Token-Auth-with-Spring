package com.zn.iotproject.exception;

import org.springframework.security.core.AuthenticationException;

public class NotFoundTokenException extends AuthenticationException {
    public NotFoundTokenException(String message) {
        super(message);
    }
}
