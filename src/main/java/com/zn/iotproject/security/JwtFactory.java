package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zn.iotproject.constant.JwtConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtFactory {
    public String generateAccessToken(UserContext context) {
        String token = null;
        Date now = new Date();
        try {
            token = JWT.create()
                    .withIssuer("ZN")
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + JwtConstant.ACCESS_TOKEN_VALID_TIME))
                    .withClaim("USERNAME", context.getUsername())
                    .sign(generateAlgorithm());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return token;
    }

    public String generateRefreshToken(UserContext context) {
        String token = null;
        Date now = new Date();
        try {
            token = JWT.create()
                    .withIssuer("ZN")
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + JwtConstant.REFRESH_TOKEN_VALID_TIME))
                    .withClaim("USERNAME", context.getUsername())
                    .sign(generateAlgorithm());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JwtConstant.SIGNING_KEY);
    }
}
