package com.zn.iotproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class ExceptionDto {
    @Data
    @AllArgsConstructor
    public static class Response {
        private Date timestamp;
        private String message;
        private String details;
    }
}
