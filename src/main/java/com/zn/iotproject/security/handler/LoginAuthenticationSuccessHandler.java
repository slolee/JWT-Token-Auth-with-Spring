package com.zn.iotproject.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zn.iotproject.domain.Users;
import com.zn.iotproject.dto.TokenDto;
import com.zn.iotproject.security.JwtFactory;
import com.zn.iotproject.security.UserContext;
import com.zn.iotproject.security.object.PostLoginAuthorizationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtFactory jwtFactory;
    @Autowired
    private ModelMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException {
        PostLoginAuthorizationToken token = (PostLoginAuthorizationToken) auth;
        UserContext user = (UserContext) token.getPrincipal();
        String jwtToken = jwtFactory.generateToken(user);
        TokenDto tokenDto = new TokenDto(jwtToken);

        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(tokenDto));
    }
}
