package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/29 10:14
 */
public enum OpsRefundStatusEnum {

    REFUNDING(1,105), //退款中
    REFUND_SUC(2,106),//退款成功
    REFUND_FAIL(3,107),//退款失败

    ;

    //ops-code
    private Integer opsCode;
    //自己维护的code
    private Integer selfCode;

    public Integer getOpsCode() {
        return opsCode;
    }

    public Integer getSelfCode() {
        return selfCode;
    }


    private OpsRefundStatusEnum(Integer opsCode, Integer selfCode) {
        this.opsCode = opsCode;
        this.selfCode = selfCode;
    }

    public static Integer getOpsCodeBySelfCode(Integer selfCode) {
        for (OpsRefundStatusEnum ele : values()) {
            if(ele.getSelfCode().equals(selfCode)) {
                return ele.getOpsCode();
            }
        }
        return null;
    }
}
