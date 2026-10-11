package org.example.cloudmall.common.exception;

import org.example.cloudmall.common.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常兜底，业务代码只管抛 ApiException，这里统一转成 CommonResult
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public CommonResult<Void> handleApiException(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode(), e.getMessage());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception e) {
        LOGGER.error("未处理异常", e);
        return CommonResult.failed("系统内部异常");
    }
}
