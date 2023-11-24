package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/29 15:30
 */
public enum OpsChannelEnum {

    DING_XIANG(2L, "丁香园"),

    YI_LU(3L, "医鹿"),

    ZHI_FU_BAO(4L, "支付宝"),

    JING_DONG(5L, "京东"),

    BAI_DU(6L, "百度健康"),

    PIN_DUO_DUO(7L, "拼多多"),

    TIAN_MAO(8L, "天猫"),
    YOU_ZAN(12L, "有赞"),

    DOU_YIN(10L, "抖音"),

    ZHI_BAI(13L, "直白"),

    MEI_TUAN(14L, "美团"),

    YI_JIA(17L, "亿家"),

    L_T(18L, "龙腾"),

    B_GY(26L, "碧桂园"),

    R_C(24L, "瑞慈"),
    X_TB(25L, "协调部"),
    ;
    private Long channelId;
    private String name;

    private OpsChannelEnum(Long channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    public static Long getIdByName(String name) {
        for (OpsChannelEnum ele : values()) {
            if(ele.getName().equals(name)) {
                return ele.getChannelId();
            }
        }
        return null;
    }

    public Long getChannelId() {
        return this.channelId;
    }

    public String getName() {
        return this.name;
    }
}
