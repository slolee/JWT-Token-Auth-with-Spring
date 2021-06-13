package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zn.iotproject.constant.AuthConstant;
import com.zn.iotproject.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtFactory {
    public String generateAccessToken(UserContext context) {
        String token = null;
        Date now = new Date();
        Users user = context.getUser();

        try {
            token = JWT.create()
                    .withIssuer("ZN")
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + AuthConstant.ACCESS_TOKEN_VALID_TIME))
                    .withClaim("USERNAME", user.getUserId())
                    .withClaim("ROLE", user.getUserRole().getRoleName())
                    .sign(generateAlgorithm());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return token;
    }

    public String generateRefreshToken(UserContext context, String value) {
        String token = null;
        Date now = new Date();
        Users user = context.getUser();

        try {
            token = JWT.create()
                    .withIssuer("ZN")
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + AuthConstant.REFRESH_TOKEN_VALID_TIME))
                    .withClaim(user.getUserId(), value)
                    .sign(generateAlgorithm());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(AuthConstant.SIGNING_KEY);
    }
}
