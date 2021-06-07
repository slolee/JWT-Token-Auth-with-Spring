package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zn.iotproject.security.exception.InvalidJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@Slf4j
public class JwtDecoder {
    private static final String SIGNING_KEY = "1q2w3e4r";

    public UserContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new InvalidJwtException(String.format("Invalid JWT Token : [%s]", token)));
        String username = decodedJWT.getClaim("USERNAME").asString();
        return new UserContext(username, "void", new ArrayList<>());
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        }catch(Exception e) {
            throw new InvalidJwtException(e.getMessage());
        }
        return Optional.ofNullable(jwt);
    }
}
