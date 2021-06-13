package com.zn.iotproject.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zn.iotproject.constant.JwtConstant;
import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.security.JwtFactory;
import com.zn.iotproject.security.UserContext;
import com.zn.iotproject.security.object.PostLoginAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtFactory jwtFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException {
        PostLoginAuthorizationToken token = (PostLoginAuthorizationToken) auth;
        UserContext user = (UserContext) token.getPrincipal();
        String accessToken = jwtFactory.generateAccessToken(user);
        String refreshToken = jwtFactory.generateRefreshToken(user);

        AuthDto.Response tokenDto = new AuthDto.Response(accessToken, refreshToken, JwtConstant.AUTH_TYPE);

        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(tokenDto));
    }
}
