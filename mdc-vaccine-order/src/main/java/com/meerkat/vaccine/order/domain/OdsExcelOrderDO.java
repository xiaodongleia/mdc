package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * <p>
 * 京东订单客户表格导入表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("ods_excel_order")
@ApiModel(value = "OdsExcelOrderDO对象", description = "京东订单客户表格导入表")
public class OdsExcelOrderDO implements Serializable {

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

    @Excel(name = "渠道来源")
//    @TableField(exist = false)
    private String orderChannel;

    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderCode;

    @Excel(name = "商品ID")
    @ApiModelProperty("商品id")
    private String goodsId;

    @Excel(name = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    @Excel(name = "SKU名称")
    @ApiModelProperty("SKU名称")
    private String skuName;

    @Excel(name = "SKU编码")
    @ApiModelProperty("商品skuId")
    private String tpSkuCode;

    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Integer num;

    @Excel(name = "商品单价")
    @ApiModelProperty("商品单价")
    private BigDecimal orderAmount;

    @Excel(name = "订单金额")
    @ApiModelProperty("订单金额")
    private BigDecimal userPayment;

    @Excel(name = "订单创建时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("订单创建时间")
    private Date orderCreateTime;

    @Excel(name = "付款时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("付款时间")
    private Date payTime;

    @Excel(name = "订单预约时间")
    @ApiModelProperty("订单预约时间")
    private String firstAppointTime;

    @Excel(name = "预约时间二")
    @ApiModelProperty("预约时间二")
    private String secondAppointTime;

    @Excel(name = "预约时间三")
    @ApiModelProperty("预约时间三")
    private String thirdAppointTime;

    @Excel(name = "预约人手机号")
    @ApiModelProperty("预约人手机号")
    private String phoneNo;

    @Excel(name = "购买人手机号")
    @ApiModelProperty("购买人电话")
    private String contactPhoneNo;

    @Excel(name = "客户名称")
    @ApiModelProperty("客户名称")
    private String name;

    @Excel(name = "身份证号码")
    @ApiModelProperty("身份证号码")
    private String idCard;

    @Excel(name = "订单状态")
    @ApiModelProperty("订单状态")
    private String orderStatus;

    @Excel(name = "退款原因")
    @ApiModelProperty("退款原因")
    private String refundReason;

    @Excel(name = "履约服务商")
    @ApiModelProperty("履约服务商")
    private String agreementMerchant;

    @Excel(name = "退款时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("退款时间")
    private Date refundTime;

    @Excel(name = "退款金额")
    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;
    /**
     * 订单类型 exam 体检 vaccine 疫苗 retail 实物
     */
    @ApiModelProperty(" 订单类型 exam 体检 vaccine 疫苗 retail 实物")
    private String bizCode;

    @ApiModelProperty("退款状态")
    private Integer refundState;

    @Excel(name = "已退款金额")
    @ApiModelProperty("已退款金额")
    @TableField(exist = false)
    private BigDecimal refundAmounts;

    @Excel(name = "收件人手机号")
    @ApiModelProperty("收件人手机号")
    @TableField(exist = false)
    private String consigneePhoneNo;

    @Excel(name = "收件人姓名")
    @ApiModelProperty("收件人姓名")
    @TableField(exist = false)
    private String consigneeName;

    @Excel(name = "收件人省")
    @ApiModelProperty("收件人省")
    @TableField(exist = false)
    private String consigneeProvince;

    @Excel(name = "收件人市")
    @ApiModelProperty("收货人市")
    @TableField(exist = false)
    private String consigneeCity;

    @Excel(name = "收件人区/镇")
    @ApiModelProperty("收件人区/镇")
    @TableField(exist = false)
    private String consigneeArea;

    @Excel(name = "子订单编号")
    @ApiModelProperty("子订单编号")
    @TableField(exist = false)
    private String childOrder;

    @Excel(name = "下单人")
    @TableField(exist = false)
    private String single;

    @Excel(name = "下单时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("下单时间")
    @TableField(exist = false)
    private Date singleTime;

    @Excel(name = "商品规格")
    @TableField(exist = false)
    private String specification;

    @Excel(name = "收件人详细地址")
    @ApiModelProperty("收件人详细地址")
    @TableField(exist = false)
    private String shippingAddress;

    @Excel(name = "买家留言")
    @ApiModelProperty("买家留言")
    @TableField(exist = false)
    private String remarkInfo;

    @Excel(name = "实付金额")
    @ApiModelProperty("实付金额")
    @TableField(exist = false)
    private BigDecimal payment;

    @Excel(name = "优惠总金额")
    @ApiModelProperty("优惠总金额")
    @TableField(exist = false)
    private BigDecimal discountAmount;

    @Excel(name = "运费")
    @ApiModelProperty("运费")
    @TableField(exist = false)
    private BigDecimal shippingCost;


    @Excel(name = "退款状态")
    @ApiModelProperty("退款状态")
    @TableField(exist = false)
    private String refundStatenName;



    @Excel(name = "商品编码")
    @ApiModelProperty("商品编码")
    @TableField(exist = false)
    private String skuCode;

    @Excel(name = "商品数量")
    @ApiModelProperty("商品数量")
    @TableField(exist = false)
    private Integer goodsCount;

    @Excel(name = "订单标签")
    @ApiModelProperty("订单标签")
    private String orderTags;

    @Excel(name = "是否预售")
    @ApiModelProperty("是否预售  否 FALSE 是 TRUE")
    private String isPresale;


    @Excel(name = "定金金额")
    @ApiModelProperty("定金金额")
    private BigDecimal deposit;


    @Excel(name = "尾款金额")
    @ApiModelProperty("尾款金额")
    private BigDecimal balance;

    @Excel(name = "定金支付时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("定金支付时间")
    private Date depositPayTime;

    @Excel(name = "运营备注")
    @ApiModelProperty("运营备注")
    private String remark;

    @Excel(name = "退款审核时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("退款审核时间")
    private Date refundAuditTime;

    @Excel(name = "店铺Id")
    @ApiModelProperty("店铺Id")
    private Long shopId;

    @Excel(name = "店铺名称")
    @ApiModelProperty("店铺名称")
    private String shopName;



    public String getOrderChannel() {
        return orderChannel;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getTpSkuCode() {
        return tpSkuCode.trim();
    }

}
