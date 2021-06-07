package com.zn.iotproject.security.object;

import com.zn.iotproject.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreLoginAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public PreLoginAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public PreLoginAuthorizationToken(UserDto.LoginRequest loginRequest) {
        this(loginRequest.getUserId(), loginRequest.getPassword());
    }

    public String getUserId() {
        return (String)super.getPrincipal();
    }
    public String getPassword() {
        return (String)super.getCredentials();
    }
}
