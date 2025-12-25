package com.carryhub.backend.dto;

public record UpdateUserRequest(
        String nickname,
        String bio,
        String avatarUrl
) {}