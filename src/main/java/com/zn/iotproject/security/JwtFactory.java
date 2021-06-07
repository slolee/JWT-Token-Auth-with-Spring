package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtFactory {
    private static final String SIGNING_KEY = "1q2w3e4r";
    private static final long ACCESS_TOKEN_VALID_TIME = 3600000;

    public String generateAccessToken(UserContext context) {
        String token = null;
        Date now = new Date();
        try {
            token = JWT.create()
                    .withIssuer("ZN")
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                    .withClaim("USERNAME", context.getUsername())
                    .sign(generateAlgorithm());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(SIGNING_KEY);
    }
}
