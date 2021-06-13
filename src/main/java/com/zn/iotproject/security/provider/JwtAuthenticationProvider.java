package com.zn.iotproject.security.provider;

import com.zn.iotproject.exception.DiscardedAccessTokenException;
import com.zn.iotproject.repository.TokenBlackListRepository;
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
    private JwtDecoder jwtDecoder;
    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreJwtProcessingToken token = (PreJwtProcessingToken) authentication;
        String accessToken = (String) token.getPrincipal();

        if (tokenBlackListRepository.existsByToken(accessToken))
            throw new DiscardedAccessTokenException(String.format("This access token is discarded : [%s]", accessToken));

        UserContext userContext = jwtDecoder.decodeAccessToken(accessToken);
        return PostLoginAuthorizationToken.getTokenFromUserContext(userContext);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreJwtProcessingToken.class.isAssignableFrom(aClass);
    }
}
