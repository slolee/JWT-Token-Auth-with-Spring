package com.zn.iotproject.security.provider;

import com.zn.iotproject.domain.Users;
import com.zn.iotproject.exception.InvalidJwtException;
import com.zn.iotproject.repository.UserRepository;
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

import java.util.Optional;

@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JwtDecoder decoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreJwtProcessingToken token = (PreJwtProcessingToken) authentication;
        String jwtToken = (String) token.getPrincipal();
        UserContext userContext = decoder.decodeJwt(jwtToken);

        Optional<Users> findUser = userRepository.findByUserId(userContext.getUsername());
        if (findUser.isPresent()) {
            return PostLoginAuthorizationToken.getTokenFromUserContext(userContext);
        }
        throw new InvalidJwtException(String.format("Invalid JWT Token : [%s]", token));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreJwtProcessingToken.class.isAssignableFrom(aClass);
    }
}
