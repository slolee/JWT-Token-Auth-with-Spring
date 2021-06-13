package com.zn.iotproject.exception;

import org.springframework.security.core.AuthenticationException;

public class AlreadyExistUserIdException extends AuthenticationException {
    public AlreadyExistUserIdException(String message) {
        super(message);
    }
}
