package com.carryhub.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // 生成签名密钥
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 生成 Token
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claim("userId", userId)        // 自定义 claim
                .subject(username)              // subject
                .issuedAt(now)                  // 签发时间
                .expiration(expiryDate)         // 过期时间
                .signWith(getSigningKey())      // 新版签名方式（自动推断算法）
                .compact();
    }

    // 解析 Claims
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 提取用户名
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 提取用户ID
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    // 检查是否过期
    public boolean isTokenExpired(String token) {
        Date expirationDate = extractAllClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }

    // 验证 Token 有效性
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}