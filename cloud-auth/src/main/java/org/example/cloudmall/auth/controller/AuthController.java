package org.example.cloudmall.auth.controller;

import java.util.HashMap;
import java.util.Map;
import org.example.cloudmall.auth.service.AuthService;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.UmsAdmin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.stp.StpUtil;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public CommonResult<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        UmsAdmin admin = authService.login(body.get("username"), body.get("password"));
        if (admin == null) {
            return CommonResult.failed("账号或密码错误");
        }
        StpUtil.login(admin.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("token", StpUtil.getTokenValue());
        data.put("username", admin.getUsername());
        data.put("nickName", admin.getNickName());
        return CommonResult.success(data);
    }
}
