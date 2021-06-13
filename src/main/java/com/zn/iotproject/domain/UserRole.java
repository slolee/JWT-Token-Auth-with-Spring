package com.zn.iotproject.domain;

import com.zn.iotproject.exception.NotFoundRoleException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    final private String roleName;
    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static UserRole getRoleByName(String roleName) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.isCorrectName(roleName)).findFirst().orElseThrow(() -> new NotFoundRoleException("존재하지 않는 권한입니다."));
    }
}
