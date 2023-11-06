package com.example.bookstoreapp.config;

import com.example.bookstoreapp.enums.TokenClaims;
import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtil {
    private static final Logger LOG = LogManager.getLogger(JwtUtil.class);
    private static final String SECRET_KEY = "4sgu1vg*u*ad90lve6b#v#eojjk@)^oat!tawp1f0#+^fw#-(y"; // Replace with your secret key
    private static final int JWT_EXPIRATION = 604800000; // Token expiration time in milliseconds (7 days)

    public String generateToken(Map<String,Object> claims) {
        long currentTimeMillis = System.currentTimeMillis();

        String token = Jwts.builder().
                setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))
                .signWith(getSignInKey())
                .setExpiration(DateUtils.addMinutes(new Date(currentTimeMillis),
                        JWT_EXPIRATION)).compact();

        log.info("Generated token: " + token);
        return token;
    }
    public Key getSignInKey() {
        byte[] secret = SECRET_KEY.getBytes();
        Key signInKey = Keys.hmacShaKeyFor(secret);
        log.info("Generated sign-in key: " + signInKey);
        return signInKey;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        Claims claims= (Jwts.parserBuilder().setSigningKey(getSignInKey()).build()).parseClaimsJws(token).getBody();
        return claims.get(TokenClaims.EMAIL.getValue()).toString();
    }
    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String getUsernameFromJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

            String username = claims.get(TokenClaims.EMAIL.getValue()).toString();
            log.info("Extracted username from token: " + username);
            return username;
        } catch (JwtException exception) {
            log.warn("Unable to extract username from token: " + exception.getMessage());
            return null;
        }
    }

    public Claims validateAndExtractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
