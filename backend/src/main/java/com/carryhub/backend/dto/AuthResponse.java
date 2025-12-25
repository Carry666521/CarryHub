package com.carryhub.backend.dto;

public record AuthResponse(
        Long userId,
        String username,
        String nickname,
        String avatarUrl,
        String token
) {}