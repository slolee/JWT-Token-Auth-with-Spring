package com.zn.iotproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthDto {
    @Data
    public static class Request {
        private String userId;
        private String accessToken;
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
