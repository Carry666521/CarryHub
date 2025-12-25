package com.carryhub.backend.controller;

import com.carryhub.backend.dto.UpdateUserRequest;
import com.carryhub.backend.entity.User;
import com.carryhub.backend.service.UserService;
import com.carryhub.backend.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private UserService userService;

    // 获取当前用户信息
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = currentUserUtil.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(user);
    }

    // 更新个人信息
    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody UpdateUserRequest request) {
        User user = currentUserUtil.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        User updated = userService.updateUser(user, request);
        return ResponseEntity.ok(updated);
    }
}