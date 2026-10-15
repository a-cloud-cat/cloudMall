package org.example.cloudmall.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.example.cloudmall.auth.service.AuthService;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.UmsAdmin;
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
    public CommonResult<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        UmsAdmin admin = authService.login(body.get("username"), body.get("password"));
        if (admin == null) {
            return CommonResult.failed("账号或密码错误");
        }
        Map<String, Object> data = new HashMap<>();
        // TODO 占位令牌，后续接入 Sa-Token 并由网关统一登录校验
        data.put("token", UUID.randomUUID().toString().replace("-", ""));
        data.put("username", admin.getUsername());
        data.put("nickName", admin.getNickName());
        return CommonResult.success(data);
    }
}
