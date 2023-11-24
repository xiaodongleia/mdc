package com.meerkat.vaccine.order.model.vo;

import lombok.Data;

/**
 *
 */
@Data
public class GoodsSecondPartyParam {

    /**
     * 商品ID
     */
    private String skuCode;
    /**
     * 购买数量
     */
    private Integer purchaseQuantity;

    private Long goodsId;

    /**
     * 商品规格
     */
    private String specification;

    /**
     * 商品单价
     */
    private Long orderAmount;

    /**
     * 商品名称
     */
    private String goodsName;


}
