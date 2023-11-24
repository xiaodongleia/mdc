package com.meerkat.common.exception;

import com.meerkat.common.api.BaseBizCodeEnum;
import com.meerkat.common.api.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理
 *
 * @author 朱家兴
 * @date 2019/10/18
 */
@Slf4j
@RestControllerAdvice
public class PersonalExceptionHandler {

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResult<Void> sendBizExceptionResponse(BizException bizException) {
        BaseResult<Void> result = new BaseResult<>();
        log.error("接口调用发生业务异常, errorCode:{}", bizException.getErrorCode(), bizException);
        return result.failed(bizException.getErrorCode(), bizException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult<Void> sendSysExceptionResponse(Exception exception) {
        if (exception instanceof BizException) {
            return this.sendBizExceptionResponse((BizException) exception);
        }
        BaseResult<Void> result = new BaseResult<>();
        log.error("接口调用发生系统异常", exception);
        return result.failed(BaseBizCodeEnum.FAILED);
    }

}
