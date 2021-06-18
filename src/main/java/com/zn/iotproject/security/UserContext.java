package com.zn.iotproject.security;

import com.zn.iotproject.domain.UserRole;
import com.zn.iotproject.domain.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserContext extends User {
    private Users user;

    private UserContext(Users user, String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        this(userId, password, authorities);
        this.user = user;
    }
    public UserContext(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
    }
    public UserContext(String userId, String role) {
        super(userId, "void", parseAuthorities(role));
    }

    public static UserContext getContextFromUser(Users user) {
        return new UserContext(user, user.getUserId(), user.getPassword(), new ArrayList<>());
    }
    public static List<SimpleGrantedAuthority> parseAuthorities(UserRole role) {
        return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority((r.getRoleName()))).collect(Collectors.toList());
    }
    public static List<SimpleGrantedAuthority> parseAuthorities(String role) {
        return parseAuthorities(UserRole.getRoleByName(role));
    }
    public Users getUser() {
        return this.user;
    }
}
