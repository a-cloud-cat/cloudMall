package org.example.cloudmall.auth.controller;

import java.util.HashMap;
import java.util.Map;
import org.example.cloudmall.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        boolean ok = authService.login(body.get("username"), body.get("password"));
        Map<String, Object> result = new HashMap<>();
        result.put("success", ok);
        result.put("message", ok ? "登录成功" : "账号或密码错误");
        return result;
    }

}
