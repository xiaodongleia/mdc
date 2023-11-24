package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/07 19:22
 */
public enum DingXiangOrgEnum {

    GUANG_ZHOU("DING_XIANG_ORG_COOKIES_GUANG_ZHOU", "广州"),

    TIAN_JIN("DING_XIANG_ORG_COOKIES_TIAN_JIN", "天津"),

    BEI_JING("DING_XIANG_ORG_COOKIES_BEI_JING", "北京"),
    ;
    private String code;
    private String name;

    DingXiangOrgEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String getCodeByName(String name) {
        for (DingXiangOrgEnum ele : values()) {
            if (ele.getName().equals(name)) {
                return ele.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (DingXiangOrgEnum ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getName();
            }
        }
        return null;
    }
}
