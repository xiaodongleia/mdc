package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 百度订单表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("ods_bai_du_order")
@ApiModel(value = "OdsBaiDuOrderDO对象", description = "百度订单表")
public class OdsBaiDuOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("数据时间")
    private Long dataTimeInt;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("商家同意退款时间")
    private Date agreeRefundTime;

    @ApiModelProperty("申请退款原因")
    private String applyRefundReason;

    @ApiModelProperty("申请退款时间")
    private Date applyRefundTime;

    @ApiModelProperty("预约日期")
    private String appointDate;

    @ApiModelProperty("联系方式")
    private String appointMobile;

    @ApiModelProperty("预约人")
    private String appointUserName;

    @ApiModelProperty("医师姓名")
    private String assignName;

    @ApiModelProperty("退款金额")
    private BigDecimal backMoneyFen;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品总价格")
    private BigDecimal goodsPriceFen;

    @ApiModelProperty("用户留言")
    private String message;

    @ApiModelProperty("订单原价(分)")
    private BigDecimal originalPriceFen;

    @ApiModelProperty("商家结算金额(分)")
    private BigDecimal paidAmountFen;

    @ApiModelProperty("平台补贴")
    private BigDecimal bdDiscount;

    @ApiModelProperty("用户实际支付金额")
    private BigDecimal userPayment;

    @ApiModelProperty("付款时间")
    private Date payTime;

    @ApiModelProperty("支付方式")
    private Integer payType;

    @ApiModelProperty("收货/购买人性别")
    private String sex;

    @ApiModelProperty("收货/购买人手机号")
    private String receMobile;

    @ApiModelProperty("收货/购买人")
    private String receName;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("商家sku编码")
    private String tpSkuCode;


}
