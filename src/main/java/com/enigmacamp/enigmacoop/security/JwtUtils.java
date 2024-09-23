package com.enigmacamp.enigmacoop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.enigmacoop.entity.UserCredential;
import com.enigmacamp.enigmacoop.model.JwtClaim;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.enigma-coop.jwt-app-name}")
    private String appName;
    @Value("${app.enigma-coop.jwt-expiration}")
    private long expirationInSecond;
    @Value("${app.enigma-coop.jwt-secret}")
    private String secretKey;

    public String generateToken(UserCredential userCredential) {
        try {
            List<String> roles = userCredential
                    .getRoles()
                    .stream()
                    .map(role -> role.getRole().name()).toList();
            return JWT
                    .create()
                    .withIssuer(appName)
                    .withSubject(userCredential.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expirationInSecond))
                    .withClaim("roles",roles)
                    .sign(Algorithm.HMAC512(secretKey));

        }catch (JWTCreationException e){
            log.error("Invalid while creating jwt token : {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public boolean verifyJwtToken(String token) {
        try{
            DecodedJWT decodedJWT = getDecodedJWT(token);
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            log.error("Invalid Verification JWT : {}",e.getMessage());
            return false;
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

    public JwtClaim getUserInfoByToken(String token){
        try{
            DecodedJWT decodedJWT = getDecodedJWT(token);
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            return JwtClaim.builder()
                    .userId(decodedJWT.getSubject())
                    .roles(roles)
                    .build();
        }catch (JWTVerificationException e){
            log.error("Invalid Verification info user JWT : {}",e.getMessage());
            return null;
        }
    }
}
