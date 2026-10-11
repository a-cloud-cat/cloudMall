package org.example.cloudmall.common.result;

import org.example.cloudmall.common.exception.IErrorCode;

/**
 * 统一 API 返回结构，实现 IErrorCode 后自身可直接充当错误码
 */
public class CommonResult<T> implements IErrorCode {

    public static final long SUCCESS = 200;
    public static final long FAILED = 500;

    private long code;
    private String message;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(SUCCESS, "操作成功", null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(SUCCESS, "操作成功", data);
    }

    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(FAILED, "操作失败", null);
    }

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(FAILED, message, null);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<>(errorCode.getCode(), message, null);
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
