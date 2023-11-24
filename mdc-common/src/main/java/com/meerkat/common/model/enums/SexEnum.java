package com.meerkat.common.model.enums;

/**
 * @author zxw
 * @date 2022/03/04 17:28
 */
public enum SexEnum {

    MAN(1, "男"),

    WOMAN(2, "女"),

    ;
    private Integer code;
    private String name;

    private SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static Integer getCodeByName(String name) {
        for (SexEnum ele : values()) {
            if(ele.getName().equals(name)) {
                return ele.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (SexEnum ele : values()) {
            if(ele.getCode().equals(code)) {
                return ele.getName();
            }
        }
        return null;
    }
}
