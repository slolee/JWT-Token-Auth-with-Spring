package com.zn.iotproject.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.security.object.PreLoginAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    protected LoginFilter(String defaultUrl) {
        super(defaultUrl);
    }

    public LoginFilter(String defaultUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this(defaultUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException {
        UserDto.LoginRequest loginRequest = new ObjectMapper().readValue(req.getReader(), UserDto.LoginRequest.class);
        PreLoginAuthorizationToken token = new PreLoginAuthorizationToken(loginRequest);
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(req, res, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(req, res, failed);
    }
}
