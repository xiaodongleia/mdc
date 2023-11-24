package com.meerkat.vaccine.order.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zxw
 * @date 2022/03/03 17:29
 */
@Data
public class MdcOrderVO {


    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("客户编号")
    private Long clientId;

    @ApiModelProperty("订单号")
    private String orderSplitId;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("订单价格（单位：分）")
    private BigDecimal orderAmount;

    @ApiModelProperty("联系电话")
    private String contactPhoneNo;

    @ApiModelProperty("买家名称")
    private String userName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("订单创建时间")
    private Date orderCreateTime;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("来源")
    private String source;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("客户手机号")
    private String phoneNo;

    @ApiModelProperty("1：男，2：女")
    private Integer sex;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("商品价格（单位：分）")
    private BigDecimal goodsPrice;

    @ApiModelProperty("第一针预约时间")
    private String firstAppointTime;

    @ApiModelProperty("第二针预约时间")
    private String secondAppointTime;

    @ApiModelProperty("第三针预约时间")
    private String thirdAppointTime;

    @ApiModelProperty("退款原因")
    private String refundReason;

    @ApiModelProperty("履约服务商")
    private String agreementMerchant;

    @ApiModelProperty("退款时间")
    private Date refundTime;

    @ApiModelProperty("sku唯一标识")
    private String skuCode;

    @ApiModelProperty("定金支付时间")
    private Date depositPayTime;

    @ApiModelProperty("时间标签")
    private Integer examWithinDays;

}
