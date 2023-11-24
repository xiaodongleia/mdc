package com.meerkat.vaccine.order.model.dto.baidu;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/03/04 15:25
 */
@Data
public class GoodsDTO {

    //数量
    private Integer amount;

    //商品规格
    private String spec;

    //商品名称
    private String titleName;

    //	商品skuId
    private Long skuId;

    //商家sku编码
    private String tpSkuCode;

    //商家goodsID
    private String goodsId;
}
