package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/03/29 10:14
 */
public enum OpsRetailOrderStatusEnum {

    WAIT_PAY(0,0), //待支付
    CLOSE(5,3),//交易关闭
    WAIT_PAY_SUC(2,2),//待预约
    DONEE(4,5),//交易完成
    ALIPAY(1,0),//待付款
    ALREADY(3,4),//已预约，在本项目中没有该状态

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


    private OpsRetailOrderStatusEnum(Integer opsCode, Integer selfCode) {
        this.opsCode = opsCode;
        this.selfCode = selfCode;
    }

    public static Integer getOpsCodeBySelfCode(Integer selfCode) {
        for (OpsRetailOrderStatusEnum ele : values()) {
            if(ele.getSelfCode().equals(selfCode)) {
                return ele.getOpsCode();
            }
        }
        return null;
    }

    public static Integer getSelfCodeByOpsCode(Integer opsCode) {
        for (OpsRetailOrderStatusEnum ele : values()) {
            if(ele.getOpsCode().equals(opsCode)) {
                return ele.getSelfCode();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getOpsCodeBySelfCode(102));
    }
}
