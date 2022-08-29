package com.exercise.nisum.util.jwt;

import com.exercise.nisum.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtToken {

    @Value("${jwt.secret.key}")
    String jwtSecretKey;

    @Value("${jwt.expiration.time.seconds}")
    String jwtExpirationTimeSeconds;



    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userId) {
        return createToken(userId);
    }

    private String createToken(String subject) {

        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(Long.parseLong(jwtExpirationTimeSeconds), ChronoUnit.SECONDS)))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        final String userId = extractUserId(token);
        return (!userId.isBlank() && !isTokenExpired(token));
    }


}
