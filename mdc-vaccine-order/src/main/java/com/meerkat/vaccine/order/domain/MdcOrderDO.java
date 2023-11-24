package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("mdc_order")
@ApiModel(value = "MdcOrderDO对象", description = "订单表")
public class MdcOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("订单号")
    private String orderSplitId;

    @ApiModelProperty("三方订单状态")
    private Integer orderStatus;

    @ApiModelProperty("系统订单状态")
    private Integer selfOrderStatus;

    @ApiModelProperty("退款状态")
    private Integer refundStatus;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品价")
    private BigDecimal goodsPrice;

    @ApiModelProperty("订单价格（单位：分）")
    private BigDecimal orderAmount;

    @ApiModelProperty("联系电话")
    private String contactPhoneNo;

    @ApiModelProperty("买家名称")
    private String userName;

    @ApiModelProperty("订单创建时间")
    private Date orderCreateTime;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("渠道")
    private String source;

    @ApiModelProperty("退款原因")
    private String refundReason;

    @ApiModelProperty("履约服务商")
    private String agreementMerchant;

//    @ApiModelProperty("沟通状态 1待沟通、2一针前已沟通、3一针后已沟通")
//    private Boolean communicateStatus;

    @ApiModelProperty("订单退款时间")
    private Date refundTime;

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty("商品goodsId")
    private String goodsId;

    @ApiModelProperty("商家sku编码")
    private String skuCode;

    @ApiModelProperty("是否通知")
    @TableField(exist = false)
    private Boolean isNotice;

    @ApiModelProperty("子订单编号")
    private String childOrder;

    @ApiModelProperty("订单商品数量")
    @TableField(exist = false)
    private Integer num;

    @ApiModelProperty("商品标签")
    private String orderTags;


    @ApiModelProperty("服务时间")
    @TableField(exist = false)
    private Date examDate;

    @ApiModelProperty("身份证号")
    @TableField(exist = false)
    private String cardId;

    @ApiModelProperty("是否预售  否 FALSE 是 TRUE")
    private String isPresale;

    @ApiModelProperty("定金金额")
    private BigDecimal deposit;


    @ApiModelProperty("尾款金额")
    private BigDecimal balance;

    @ApiModelProperty("定金支付时间")
    private Date depositPayTime;

    @ApiModelProperty("预约时间")
    private String firstAppointTime;

    @ApiModelProperty("时间标签")
    private Integer examWithinDays;

    @ApiModelProperty("运营备注")
    @TableField(exist = false)
    private String operateRemark;

    @ApiModelProperty("二方核销时间")
    @TableField(exist = false)
    private Date secondOrderCheckTime;

    @Excel(name = "退款审核时间")
    @ApiModelProperty("退款审核时间")
    private Date refundAuditTime;

    @Excel(name = "店铺Id")
    @ApiModelProperty("店铺Id")
    private Long shopId;

    @Excel(name = "店铺名称")
    @ApiModelProperty("店铺名称")
    private String shopName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MdcOrderDO that = (MdcOrderDO) o;
        return Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatus);
    }

}
