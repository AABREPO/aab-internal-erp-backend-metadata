package com.aabuilders.Dashboard.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT token operations.
 * Handles token generation, validation, and extraction of claims.
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "aabuilders_jwt_secret_key_2024_very_long_and_secure_key_for_token_generation";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final long tokenValidityMs;

    public JwtUtil(@Value("${app.jwt.expiry-days:30}") long jwtExpiryDays) {
        this.tokenValidityMs = jwtExpiryDays * 24L * 60L * 60L * 1000L;
    }

    /**
     * Generates a JWT token for the given username.
     * 
     * @param username The username to include in the token
     * @return Generated JWT token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Creates a JWT token with the given claims and subject.
     * 
     * @param claims The claims to include in the token
     * @param subject The subject (username) of the token
     * @return Generated JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     * 
     * @param token The JWT token
     * @return Username from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JWT token.
     * 
     * @param token The JWT token
     * @return Expiration date from the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from a JWT token.
     * 
     * @param token The JWT token
     * @param claimsResolver Function to extract the specific claim
     * @return The extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token.
     * 
     * @param token The JWT token
     * @return All claims from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if a JWT token is expired.
     * 
     * @param token The JWT token
     * @return true if token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates a JWT token for a given username.
     * 
     * @param token The JWT token to validate
     * @param username The username to validate against
     * @return true if token is valid for the username, false otherwise
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    public boolean isTokenValid(String token) {
        try {
            String email = extractUsername(token);
            return email != null && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
} 