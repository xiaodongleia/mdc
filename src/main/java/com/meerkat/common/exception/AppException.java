package com.meerkat.common.exception;


import com.meerkat.common.api.BizCode;

import java.text.MessageFormat;

/**
 * @author huangwei
 */
public abstract class AppException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private String errorCode;

    public AppException() {
    }

    public AppException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public AppException(BizCode errorInfo) {
        super(errorInfo.getMessage());
        this.errorCode = errorInfo.getCode();
    }

    public AppException(BizCode errorInfo, Object... params) {
        super(MessageFormat.format(errorInfo.getMessage(), params));
        this.errorCode = errorInfo.getCode();
    }

    public AppException(BizCode errorInfo, Throwable e) {
        super(errorInfo.getMessage(), e);
        this.errorCode = errorInfo.getCode();
    }

    public String getErrorCode() {
        return this.errorCode;
    }

}
