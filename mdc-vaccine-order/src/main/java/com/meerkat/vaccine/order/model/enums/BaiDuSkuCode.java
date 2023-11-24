package com.meerkat.vaccine.order.model.enums;

/**
 * @author zxw
 * @date 2022/04/13 10:34
 */
public enum BaiDuSkuCode {

    BD_01("vac_16497527640251478879","BD_01"),
    BD_02("vac_16468794162138129056","BD_01"),
    BD_03("vac_16436925170221422203","BD_01"),
    BD_04("1011648609260625005","BD_01"),
    BD_05("vac_16489108471014623390","BD_01"),
    BD_06("vac_16474035551300513185","BD_01"),
    BD_07("vac_16473966691807552563","BD_01"),
    BD_08("vac_16482991790271497147","BD_01"),
    BD_09("vac_16487805340214968708","BD_01"),
    BD_10("vac_16472206672113454494","BD_01"),
    BD_11("vac_16456718930514966676","BD_01"),
    BD_12("vac_16472745611225022920","BD_01"),
    BD_13("vac_16471465561254781923","BD_01"),
    BD_14("vac_16477617690121178059","BD_01"),
    BD_15("vac_16434307331841674569","BD_01"),
    BD_16("vac_16495866841087670870","BD_01"),
    BD_17("vac_16475047140334660664","BD_01"),
    BD_18("vac_16470661301202249324","BD_01"),
    BD_19("vac_16484518641907647410","BD_01"),
    ;

    //二方订单号
    private String orderSplitId;
    //二方skuCode
    private String skuCode;

    public String getOrderSplitId() {
        return orderSplitId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    private BaiDuSkuCode(String orderSplitId, String skuCode) {
        this.orderSplitId = orderSplitId;
        this.skuCode = skuCode;
    }

    public static String getCodeById(String orderSplitId) {
        for (BaiDuSkuCode ele : values()) {
            if(ele.getOrderSplitId().equals(orderSplitId)) {
                return ele.getSkuCode();
            }
        }
        return null;
    }
}
