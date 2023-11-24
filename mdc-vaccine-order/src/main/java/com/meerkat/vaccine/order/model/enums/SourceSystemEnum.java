package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/03 14:26
 */
public enum SourceSystemEnum {

    DING_XIANG("dingXiang", "丁香园"),

    YI_LU("yiLu", "医鹿"),

    ZHI_FU_BAO("zhifubao", "支付宝"),

    JING_DONG("jingDong", "京东"),

    BAI_DU("baiDu", "百度健康"),

    PIN_DUO_DUO("pinDuoDuo", "拼多多"),


    ;
    private String code;
    private String name;

    private SourceSystemEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getCodeByName(String name) {
        for (SourceSystemEnum ele : values()) {
            if(ele.getName().equals(name)) {
                return ele.getCode();
            }
        }
        return null;
    }


    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
