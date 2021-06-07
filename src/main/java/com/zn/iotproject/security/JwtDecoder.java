package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zn.iotproject.exception.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtDecoder {
    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);
    private static final String SIGNING_KEY = "1q2w3e4r";

    // 유효한 JWT인지 검증하는 부분.
    public UserContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new InvalidJwtException("유효한 토큰이 아닙니다."));
        String username = decodedJWT.getClaim("USERNAME").asString();
        return new UserContext(username, "void", new ArrayList<>());
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        }catch(Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(jwt);
    }
}
