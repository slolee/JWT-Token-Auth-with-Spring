package com.zn.iotproject.dto;

import com.zn.iotproject.domain.UserRole;
import lombok.Data;

public class UserDto {
    @Data
    public static class SignupRequest {
        private String userId;
        private String email;
        private String password;
    }

    @Data
    public static class LoginRequest {
        private String userId;
        private String password;
    }

    @Data
    public static class Response {
        private String userId;
        private String email;
        private UserRole userRole;
    }
}
