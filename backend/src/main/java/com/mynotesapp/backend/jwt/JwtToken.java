package com.mynotesapp.backend.jwt;

import com.mynotesapp.backend.domain.entity.UserEntity;
import com.mynotesapp.backend.dto.user.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class JwtToken {

    private final Environment environment;

    public String generateByUserCredentials(String email) {
        long expirationTime = getExpirationTime();
        Instant now = Instant.now();
        SecretKey secretKey = getSecretKey();

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(Date.from(now.plusMillis(expirationTime)))
                .setIssuedAt(Date.from(now))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private SecretKey getSecretKey() {
//        String secret = environment.getProperty(environment.getProperty("token.secret"));
        String secret = "fUjXn2r5u8x/A?D(B+KbPeSgVkYp2s6v9y$B&E)H@McQfTjWmZq489w!z%C*F-Ja";
        byte[] secretBytes = Base64.getEncoder().encode(secret.getBytes());

        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    private Long getExpirationTime() {
//        return Long.parseLong(environment.getProperty("token.expiration_time"));
        return Long.parseLong("3600000");
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
