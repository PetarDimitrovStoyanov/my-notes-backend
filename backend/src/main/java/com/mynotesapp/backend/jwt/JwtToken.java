package com.mynotesapp.backend.jwt;

import com.mynotesapp.backend.exception.TokenNotValidException;
import com.mynotesapp.backend.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtToken {

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.expiration_time}")
    private long tokenExpirationTime;

    public String generateByUserCredentials(String email) {
        long expirationTime = tokenExpirationTime;
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
        byte[] secretBytes = Base64.getEncoder().encode(tokenSecret.getBytes());

        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token.trim(), Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception exception) {
            throw new TokenNotValidException(token);
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public HttpHeaders addTokenHeader(String email) {
        HttpHeaders headers = new HttpHeaders();
        String token = generateByUserCredentials(email);
        headers.set(Constants.AUTHORIZATION, String.format("%s%s", Constants.BEARER_SPACE, token));
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, Constants.AUTHORIZATION);

        return headers;
    }
}
