package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zn.iotproject.constant.AuthConstant;
import com.zn.iotproject.exception.NotFoundTokenException;
import org.springframework.util.StringUtils;

public class Utils {
    public static String resolveToken(String authorization_header) {
        if (StringUtils.isEmpty(authorization_header) || authorization_header.length() <= AuthConstant.AUTH_TYPE.length())
            throw new NotFoundTokenException("Not found token in http request header.");
        return authorization_header.substring(AuthConstant.AUTH_TYPE.length() + 1);
    }
    public static Boolean isExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(AuthConstant.SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        }catch (TokenExpiredException e) {
            return true;
        }
        return false;
    }
}
