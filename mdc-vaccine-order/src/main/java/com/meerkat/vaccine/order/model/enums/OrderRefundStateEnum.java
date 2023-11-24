package com.meerkat.vaccine.order.model.enums;

public enum OrderRefundStateEnum {

    REFUNDING(1, "退款中"),

    REFUNDED(2, "已退款"),

    REFUSE(3, "拒绝退款"),
    ;


    private Integer code;
    private  String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    OrderRefundStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Integer getByCode( String desc) {
        for (OrderRefundStateEnum orderRefundStateEnum : OrderRefundStateEnum.values()) {
            if (orderRefundStateEnum.getDesc().equals(desc)) {
                return orderRefundStateEnum.getCode();
            }
        }
        return null;
    }
}
