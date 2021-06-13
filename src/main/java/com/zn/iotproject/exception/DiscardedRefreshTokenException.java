package com.zn.iotproject.exception;

public class DiscardedRefreshTokenException extends RuntimeException {
    public DiscardedRefreshTokenException(String message) {
        super(message);
    }
}
