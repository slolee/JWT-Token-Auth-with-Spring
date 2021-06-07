package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtFactory {
    private static final String SIGNING_KEY = "1q2w3e4r";

    public String generateToken(UserContext context) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("ZN")
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
