package com.meerkat.common.api;

/**
 * 常用API返回对象
 *
 * @author zhujx
 */
public enum BaseBizCodeEnum implements BizCode {
    /**
     * 成功
     */
    SUCCESS("200", "success"),

    /**
     * 失败
     */
    FAILED("-1000", "failed"),

    /**
     * 权限不足
     */
    UNAUTHORIZED("401", "unauthorized"),

    /**
     * 字段为空
     */
    FIELD_IS_NULL("-2000", "field is null"),

    ;

    private final String code;
    private final String message;

    BaseBizCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
