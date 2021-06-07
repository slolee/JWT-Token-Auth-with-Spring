package com.zn.iotproject.security;

import com.zn.iotproject.domain.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserContext extends User {
    public UserContext(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
    }

    public static UserContext getContextFromUser(Users user) {
        return new UserContext(user.getUserId(), user.getPassword(), new ArrayList<>());
    }
}
