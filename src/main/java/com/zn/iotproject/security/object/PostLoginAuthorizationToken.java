package com.zn.iotproject.security.object;

import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.security.UserContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostLoginAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public PostLoginAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
    public static PostLoginAuthorizationToken getTokenFromUserContext(UserContext context) {
        return new PostLoginAuthorizationToken(context, context.getPassword(), context.getAuthorities());
    }
}
