package com.zn.iotproject.security;

import com.zn.iotproject.constant.JwtConstant;
import com.zn.iotproject.exception.NotFoundTokenException;
import org.springframework.util.StringUtils;

public class Utils {
    public static String resolveToken(String authorization_header) {
        if (StringUtils.isEmpty(authorization_header) || authorization_header.length() <= JwtConstant.AUTH_TYPE.length())
            throw new NotFoundTokenException("Not found token in http request header.");
        return authorization_header.substring(JwtConstant.AUTH_TYPE.length() + 1);
    }
}
