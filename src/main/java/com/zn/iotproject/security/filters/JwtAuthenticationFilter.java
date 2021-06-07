package com.zn.iotproject.security.filters;

import com.zn.iotproject.security.object.PreJwtProcessingToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    protected JwtAuthenticationFilter(RequestMatcher matcher) {
        super(matcher);
    }

    public JwtAuthenticationFilter(RequestMatcher matcher, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this(matcher);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String authorization_header = req.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization_header) || authorization_header.length() < "Bearer ".length()) {
            authorization_header = "Invalid Token";
        }
        PreJwtProcessingToken token = new PreJwtProcessingToken(authorization_header.substring("Bearer ".length()));
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(req, res, authResult);
        chain.doFilter(req, res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed)
            throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(req, res, failed);
    }
}
