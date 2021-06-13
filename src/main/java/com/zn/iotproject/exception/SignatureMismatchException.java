package com.zn.iotproject.exception;

import org.springframework.security.core.AuthenticationException;

public class SignatureMismatchException extends AuthenticationException {
    public SignatureMismatchException(String message) {
        super(message);
    }
}
