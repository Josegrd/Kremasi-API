package com.gardio.kremasi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gardio.kremasi.entity.Nasabah;
import com.gardio.kremasi.entity.UserCredential;
import com.gardio.kremasi.model.JwtClaim;
import com.gardio.kremasi.repository.EmployeeRepository;
import com.gardio.kremasi.repository.NasabahRepository;
import com.gardio.kremasi.service.NasabahService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.kremasi.jwt-app-name}")
    private String appName;
    @Value("${app.kremasi.jwt-expiration}")
    private long expirationInSecond;
    @Value("${app.kremasi.jwt-secret}")
    private String secretKey;

    private NasabahRepository nasabahRepository;
    private EmployeeRepository employeeRepository;
    private NasabahService nasabahService;

    @Autowired
    public JwtUtils(NasabahRepository nasabahRepository, EmployeeRepository employeeRepository) {
        this.nasabahRepository = nasabahRepository;
        this.employeeRepository = employeeRepository;
    }

    public String generateToken(UserCredential userCredential) {
        try {
            List<String> roles = userCredential
                    .getRoles()
                    .stream()
                    .map(role -> role.getRole().name()).toList();
            String username = userCredential.getUsername();
            String nasabahId = "0";
            Nasabah nasabah = nasabahRepository.getNasabahByUserCredential_Id(userCredential.getId());
            if(nasabah != null){
                nasabahId = String.valueOf(nasabah.getId());
            }

            return JWT
                    .create()
                    .withIssuer(appName)
                    .withSubject(userCredential.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expirationInSecond))
                    .withClaim("roles",roles)
                    .withClaim("username",username)
                    .withClaim("nasabahId",nasabahId)
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
