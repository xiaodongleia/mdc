package com.meerkat.common.api;


import cn.hutool.json.JSONObject;

/**
 * 顶级response
 *
 * @author zhujx
 */
public class BaseResult<T> {


    /**
     * 状态码
     */
    private String code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;

    public BaseResult() {
        this.code = BaseBizCodeEnum.SUCCESS.getCode();
        this.message = BaseBizCodeEnum.SUCCESS.getMessage();
    }

    public BaseResult(T data) {
        this.code = BaseBizCodeEnum.SUCCESS.getCode();
        this.message = BaseBizCodeEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public BaseResult<T> code(String code) {
        this.code = code;
        return this;
    }

    public BaseResult<T> msg(String message) {
        this.message = message;
        return this;
    }

    public BaseResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public BaseResult<T> success() {
        this.code = BaseBizCodeEnum.SUCCESS.getCode();
        this.message = BaseBizCodeEnum.SUCCESS.getMessage();
        return this;
    }

    public BaseResult<T> success(T data) {
        this.code = BaseBizCodeEnum.SUCCESS.getCode();
        this.message = BaseBizCodeEnum.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public BaseResult<T> failed() {
        this.code = BaseBizCodeEnum.FAILED.getCode();
        this.message = BaseBizCodeEnum.FAILED.getMessage();
        return this;
    }

    public BaseResult<T> failed(String message) {
        this.code = BaseBizCodeEnum.FAILED.getCode();
        this.message = message;
        return this;
    }

    public BaseResult<T> failed(BizCode bizCode) {
        this.code = bizCode.getCode();
        this.message = bizCode.getMessage();
        return this;
    }

    public BaseResult<T> failed(String code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }


}