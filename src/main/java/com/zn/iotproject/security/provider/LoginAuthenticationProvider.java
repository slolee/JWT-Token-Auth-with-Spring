package com.zn.iotproject.security.provider;

import com.zn.iotproject.domain.Users;
import com.zn.iotproject.exception.InvalidUserException;
import com.zn.iotproject.repository.UserRepository;
import com.zn.iotproject.security.UserContext;
import com.zn.iotproject.security.object.PostLoginAuthorizationToken;
import com.zn.iotproject.security.object.PreLoginAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreLoginAuthorizationToken token = (PreLoginAuthorizationToken) authentication;
        String userId = token.getUserId();
        String password = token.getPassword();

        Users findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidUserException(String.format("Not found user : [%s].", userId)));
        if (checkPassword(password, findUser)) {
            return PostLoginAuthorizationToken.getTokenFromUserContext(UserContext.getContextFromUser(findUser));
        }
        throw new InvalidUserException(String.format("Invalid password of [%s].", userId));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreLoginAuthorizationToken.class.isAssignableFrom(aClass);
    }
    public boolean checkPassword(String password, Users user) {
        return encoder.matches(password, user.getPassword());
    }
}
