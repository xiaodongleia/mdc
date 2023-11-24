package com.meerkat.notice.adapter.model.enums;

/**
 * @author zxw
 * @date 2022/03/04 17:28
 */
public enum RobotNoticeEnum {

    /**
     * 订单状态推送
     */
    ORDER_STATUS(1, "订单状态推送"),

    /**
     * 订单履约时间推送
     */
    ORDER_APPOINT(2, "订单履约时间推送"),

    ;
    private final Integer code;

    private final String name;

    RobotNoticeEnum(Integer code, String name) {
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
        for (RobotNoticeEnum ele : values()) {
            if (ele.getName().equals(name)) {
                return ele.getCode();
            }
        }
        return null;
    }

}
