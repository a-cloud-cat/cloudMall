package org.example.cloudmall.common.exception;

/**
 * 业务异常，service 层校验不通过时抛出，由 GlobalExceptionHandler 统一转成返回体
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(IErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
