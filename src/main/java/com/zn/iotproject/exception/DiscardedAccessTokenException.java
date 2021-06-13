package com.zn.iotproject.exception;

import org.springframework.security.core.AuthenticationException;

public class DiscardedAccessTokenException extends AuthenticationException {
    public DiscardedAccessTokenException(String message) {
        super(message);
    }
}
