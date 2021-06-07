package com.zn.iotproject.security.provider;

import com.zn.iotproject.domain.Users;
import com.zn.iotproject.repository.UserRepository;
import com.zn.iotproject.security.UserContext;
import com.zn.iotproject.security.object.PostLoginAuthorizationToken;
import com.zn.iotproject.security.object.PreLoginAuthorizationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Not Found User : [%s]", userId)));
        if (checkPassword(password, findUser)) {
            return PostLoginAuthorizationToken.getTokenFromUserContext(UserContext.getContextFromUser(findUser));
        }
        throw new UsernameNotFoundException(String.format("Not Found User : [%s]", userId));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreLoginAuthorizationToken.class.isAssignableFrom(aClass);
    }
    public boolean checkPassword(String password, Users user) {
        return encoder.matches(password, user.getPassword());
    }
}
