package com.carryhub.backend.filter;

import com.carryhub.backend.entity.User;
import com.carryhub.backend.service.UserService;
import com.carryhub.backend.utils.CurrentUserUtil;
import com.carryhub.backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extractUserId(token);
                User user = userService.getUserById(userId);
                currentUserUtil.setCurrentUser(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}