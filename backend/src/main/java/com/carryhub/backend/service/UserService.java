package com.carryhub.backend.service;

import com.carryhub.backend.dto.RegisterRequest;
import com.carryhub.backend.dto.UpdateUserRequest;
import com.carryhub.backend.entity.User;
import com.carryhub.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("用户名已存在");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("邮箱已存在");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setNickname(request.username());

        return userRepository.save(user);
    }

    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail));
    }

    public User updateUser(User user, UpdateUserRequest request) {
        if (request.nickname() != null) user.setNickname(request.nickname());
        if (request.bio() != null) user.setBio(request.bio());
        if (request.avatarUrl() != null) user.setAvatarUrl(request.avatarUrl());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}