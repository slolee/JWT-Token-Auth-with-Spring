package com.zn.iotproject.security.object;

import com.zn.iotproject.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreLoginAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public PreLoginAuthorizationToken(UserDto.LoginRequest loginRequest) {
        super(loginRequest.getUserId(), loginRequest.getPassword());
    }
    public PreLoginAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public String getUserId() {
        return (String)super.getPrincipal();
    }
    public String getPassword() {
        return (String)super.getCredentials();
    }
}
