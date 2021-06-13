package com.zn.iotproject.dto;

import com.zn.iotproject.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthDto {
    @Data
    public static class RefreshRequest {
        private String userId;
        private String refreshToken;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private String accessToken;
        private String refreshToken;
        private String type;
    }

    @Data
    @AllArgsConstructor
    public static class CheckOverlapResponse {
        private Boolean overlap;
    }
}
