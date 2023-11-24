package com.meerkat.vaccine.order.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author LENOVO
 * @version 1.0
 * @description: TODO
 * @date 2022/5/30 16:32
 */
public class RetailGoodsSnapshot {

    @ApiModelProperty("商品编码")
    private String skuCode;

    /**
     * 购买数量
     */
    @ApiModelProperty("商品数量")
    private Integer purchaseQuantity;
    @ApiModelProperty("商品规格")
    private String specification;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("商品单价")
    private Long orderAmount;

    @ApiModelProperty("商品名称")
    private String goodsName;


    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }


    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

}
