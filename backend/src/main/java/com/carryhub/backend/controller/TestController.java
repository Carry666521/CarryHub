package com.carryhub.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, CarryHub Backend is Running! 🚀";
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "running");
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("service", "CarryHub Backend");
        status.put("version", "1.0.0");
        return status;
    }
}