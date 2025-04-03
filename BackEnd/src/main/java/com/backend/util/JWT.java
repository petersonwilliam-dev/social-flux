package com.backend.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class JWT {

    private static final Key SECRET_KEY = generateKey(PropertiesUtil.getProperties().getProperty("jwt.secret"));

    public static String generateJwt(String json) {

        long expirationTime = (1000 * 60 * 60) * 48;
        return Jwts.builder()
                .setSubject(json)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String bearerToken) {

        try {

            if (bearerToken == null || !bearerToken.contains(" ")) return false;

            String token = bearerToken.substring(bearerToken.indexOf(" ")).trim();

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getToken(String bearerToken) {
        String token = bearerToken.substring(bearerToken.indexOf(" ")).trim();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    private static Key generateKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
