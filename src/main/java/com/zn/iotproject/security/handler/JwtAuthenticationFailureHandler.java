package com.zn.iotproject.security.handler;

import com.zn.iotproject.security.exception.InvalidJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (e instanceof InvalidJwtException) {
            log.error(e.getClass().getName());
            log.error(e.getMessage());
        }
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
