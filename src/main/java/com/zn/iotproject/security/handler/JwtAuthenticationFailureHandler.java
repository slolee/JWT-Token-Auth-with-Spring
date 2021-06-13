package com.zn.iotproject.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zn.iotproject.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        ExceptionDto.Response response = new ExceptionDto.Response(new Date(), e.getClass().getSimpleName(), e.getMessage());
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.getWriter().write(new ObjectMapper().writeValueAsString(response));
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
