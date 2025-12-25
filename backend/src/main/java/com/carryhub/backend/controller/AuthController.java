package com.carryhub.backend.controller;

import com.carryhub.backend.dto.AuthResponse;
import com.carryhub.backend.dto.LoginRequest;
import com.carryhub.backend.dto.RegisterRequest;
import com.carryhub.backend.entity.User;
import com.carryhub.backend.service.UserService;
import com.carryhub.backend.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // 前端开发时允许跨域
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        String token = jwtUtil.generateToken(user
                .getId(), user.getUsername());
        AuthResponse response = new AuthResponse(user.getId(), user.getUsername(), user.getNickname(), user.getAvatarUrl(), token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.findByUsernameOrEmail(request.usernameOrEmail())
                .filter(u -> userService.checkPassword(request.password(), u.getPassword()))
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        AuthResponse response = new AuthResponse(user.getId(), user.getUsername(), user.getNickname(), user.getAvatarUrl(), token);
        return ResponseEntity.ok(response);
    }
}