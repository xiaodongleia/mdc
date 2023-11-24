package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/29 10:14
 */
public enum OpsOrderStatusEnum {

    WAIT_PAY(0,101), //待支付
    CLOSE(3,103),//交易关闭
    WAIT_PAY_SUC(2,102),//待预约
    DONEE(5,104),//交易完成
    ALIPAY(0,108),//待付款
    ALREADY(4,001),//已预约，在本项目中没有该状态

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


    private OpsOrderStatusEnum(Integer opsCode, Integer selfCode) {
        this.opsCode = opsCode;
        this.selfCode = selfCode;
    }

    public static Integer getOpsCodeBySelfCode(Integer selfCode) {
        for (OpsOrderStatusEnum ele : values()) {
            if(ele.getSelfCode().equals(selfCode)) {
                return ele.getOpsCode();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getOpsCodeBySelfCode(102));
    }
}
