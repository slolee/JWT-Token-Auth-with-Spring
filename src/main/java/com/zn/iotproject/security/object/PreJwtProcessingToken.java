package com.zn.iotproject.security.object;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreJwtProcessingToken extends UsernamePasswordAuthenticationToken {
    public PreJwtProcessingToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public PreJwtProcessingToken(String token) {
        this(token, token.length());
    }
}
