package org.example.cloudmall.auth.controller;

import org.example.cloudmall.common.exception.ApiException;
import org.example.cloudmall.common.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证统一返回与全局异常的演示接口
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/ok")
    public CommonResult<String> ok() {
        return CommonResult.success("demo ok");
    }

    @GetMapping("/fail")
    public CommonResult<Void> fail() {
        throw new ApiException("演示业务异常");
    }
}
