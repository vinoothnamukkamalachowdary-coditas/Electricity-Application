package com.example.demo_Electricity_App.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "9f8a7c6b5d4e3f2a1b0c9d8e7f6a5b4c9f8a7c6b5d4e3f2a1b0c9d8e7f6a5b4c";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long EXPIRATION = 1000 * 60 * 60;

        /** For Coditas employees (master schema). */
        public String generateMasterToken(String email, String role) {
            return build(email, role, "master");
        }

        /** For electricity-provider users (tenant schema). */
        public String generateTenantToken(String email, String role, String schemaName) {
            return build(email, role, schemaName);
        }

        private String build(String email, String role, String tenant) {
            long now = System.currentTimeMillis();
            return Jwts.builder()
                    .setSubject(email)
                    .claim("role",  role)
                    .claim("tenant", tenant)
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + EXPIRATION))
                    .signWith(key,SignatureAlgorithm.HS256)
                    .compact();
        }

        // Token validation
        public boolean isValid(String token) {
            try {
                parseClaims(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        // Claim extraction

        public String getEmail(String token) {
            return parseClaims(token).getSubject();
        }

        public String getRole(String token) {
            return parseClaims(token).get("role", String.class);
        }

        public String getTenant(String token) {
            return parseClaims(token).get("tenant", String.class);
        }

        public Claims parseClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }
}

