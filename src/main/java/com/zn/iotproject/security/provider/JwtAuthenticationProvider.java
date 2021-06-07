package com.zn.iotproject.security.provider;

import com.zn.iotproject.security.JwtDecoder;
import com.zn.iotproject.security.UserContext;
import com.zn.iotproject.security.object.PostLoginAuthorizationToken;
import com.zn.iotproject.security.object.PreJwtProcessingToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JwtDecoder decoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreJwtProcessingToken token = (PreJwtProcessingToken) authentication;
        String jwtToken = (String) token.getPrincipal();
        UserContext userContext = decoder.decodeJwt(jwtToken);
        return PostLoginAuthorizationToken.getTokenFromUserContext(userContext);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreJwtProcessingToken.class.isAssignableFrom(aClass);
    }
}
