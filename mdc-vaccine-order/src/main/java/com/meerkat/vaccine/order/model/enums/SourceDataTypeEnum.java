package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/03 14:26
 */
public enum SourceDataTypeEnum {

    API("API", "接口文档"),

    EXCEL("EXCEL", "表格导入"),

    ;
    private String code;
    private String name;

    SourceDataTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
