package com.zn.iotproject.constant;

public class JwtConstant {
    public static final String AUTH_TYPE = "bearer";
    public static final String SIGNING_KEY = "1q2w3e4r";
//    public static final long ACCESS_TOKEN_VALID_TIME = 3600000;
//    public static final long REFRESH_TOKEN_VALID_TIME = 604800000;
    public static final long ACCESS_TOKEN_VALID_TIME = 60000;
    public static final long REFRESH_TOKEN_VALID_TIME = 300000;
}
