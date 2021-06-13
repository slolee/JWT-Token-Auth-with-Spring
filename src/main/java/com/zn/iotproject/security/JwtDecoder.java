package com.zn.iotproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zn.iotproject.constant.AuthConstant;
import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.exception.ExpiredTokenException;
import com.zn.iotproject.exception.InvalidJwtException;
import com.zn.iotproject.exception.SignatureMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class JwtDecoder {
    public AuthDto.RefreshKey decodeRefreshToken(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new InvalidJwtException(String.format("Invalid JWT token : [%s].", token)));
        String username = decodedJWT.getClaim("USERNAME").asString();
        String key = decodedJWT.getClaim("KEY").asString();

        if (username == null || key == null)
            throw new InvalidJwtException("Invalid token without claim: (USERNAME, KEY).");
        return new AuthDto.RefreshKey(username, key);
    }

    public UserContext decodeAccessToken(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new InvalidJwtException(String.format("Invalid JWT token : [%s].", token)));
        String username = decodedJWT.getClaim("USERNAME").asString();
        String role = decodedJWT.getClaim("ROLE").asString();

        if (username == null || role == null)
            throw new InvalidJwtException("Invalid token without claim: (USERNAME, ROLE).");
        return new UserContext(username, role);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt;
        try {
            Algorithm algorithm = Algorithm.HMAC256(AuthConstant.SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        }catch (TokenExpiredException e) {
            throw new ExpiredTokenException(e.getMessage());
        }catch (SignatureVerificationException e) {
            throw new SignatureMismatchException(e.getMessage());
        }catch (Exception e) {
            throw new InvalidJwtException(e.getMessage());
        }
        return Optional.ofNullable(jwt);
    }

}
