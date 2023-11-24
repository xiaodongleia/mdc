package com.meerkat.vaccine.order.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author LENOVO
 * @version 1.0
 * @description: TODO
 * @date 2022/5/31 10:57
 */
public class OpsRetailOrderVO {


    @ApiModelProperty("订单状态 0=待支付 1 =支付中 2=已支付 3=已关闭 4=已预约 5=已完成 6=预约失败 7=预约中")
    private Integer state;


    @ApiModelProperty("外部id")
    private String outOrderNum;

    @ApiModelProperty("渠道id")
    private Long chorgaId;

    @ApiModelProperty("sku唯一标识")
    private String skuCode;

    @ApiModelProperty("子订单编号")
    private String childOrderNum;


    @ApiModelProperty("买家留言")
    private String remark;

    @ApiModelProperty("运费")
    private Long shippingCost;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date paymentTime;

    @ApiModelProperty("收货人省")
    private String consigneeProvince;

    @ApiModelProperty("收货人市")
    private String consigneeCity;

    @ApiModelProperty("收件人区/镇")
    private String consigneeArea;

    @ApiModelProperty("收件人详细地址")
    private String shippingAddress;
    @ApiModelProperty("收件人手机号")
    private String acceptorMobile;

    @ApiModelProperty("收件人姓名")
    private String acceptorName;

    @ApiModelProperty("已退款金额")
    private Long refundAmount;

    @ApiModelProperty("订单总金额")
    private Long totalAmount;

    /**
     * 下单人信息
     */
    @ApiModelProperty("收件人详细地址")
    private OperatorSnapshot operatorSnapshot;

    private RetailGoodsSnapshot retailGoodsSnapshot;


    @ApiModelProperty("服务时间")
    private RefundParam refundParam;

    @ApiModelProperty("实付金额")
    private Long payAmount;

    @ApiModelProperty("优惠总金额")
    private Long discountAmount;

    @ApiModelProperty("退款状态")
    private Integer refundState;

    @ApiModelProperty("商品单价")
    private Long orderAmount;

    @ApiModelProperty("订单类型：0 服务订单 1 实物订单 2 充值订单")
    private Integer orderType;

    @ApiModelProperty("类型")
    private Integer chorgaType;
    @ApiModelProperty("订单业务类型")
    private String bizCode;

    @ApiModelProperty("订单来源：0 微信小程序")
    private Integer sourceType;

    @ApiModelProperty("下单时间")
    private Date gmtCreated;

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Integer getChorgaType() {
        return chorgaType;
    }

    public void setChorgaType(Integer chorgaType) {
        this.chorgaType = chorgaType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public RetailGoodsSnapshot getRetailGoodsSnapshot() {
        return retailGoodsSnapshot;
    }

    public void setRetailGoodsSnapshot(RetailGoodsSnapshot retailGoodsSnapshot) {
        this.retailGoodsSnapshot = retailGoodsSnapshot;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public RefundParam getRefundParam() {
        return refundParam;
    }

    public void setRefundParam(RefundParam refundParam) {
        this.refundParam = refundParam;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getAcceptorMobile() {
        return acceptorMobile;
    }

    public void setAcceptorMobile(String acceptorMobile) {
        this.acceptorMobile = acceptorMobile;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public OperatorSnapshot getOperatorSnapshot() {
        return operatorSnapshot;
    }

    public void setOperatorSnapshot(OperatorSnapshot operatorSnapshot) {
        this.operatorSnapshot = operatorSnapshot;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeArea() {
        return consigneeArea;
    }

    public void setConsigneeArea(String consigneeArea) {
        this.consigneeArea = consigneeArea;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    private RetailGoodsSnapshot retailGoodsSnapshots;


    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getOutOrderNum() {
        return outOrderNum;
    }

    public void setOutOrderNum(String outOrderNum) {
        this.outOrderNum = outOrderNum;
    }

    public Long getChorgaId() {
        return chorgaId;
    }

    public void setChorgaId(Long chorgaId) {
        this.chorgaId = chorgaId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getChildOrderNum() {
        return childOrderNum;
    }

    public void setChildOrderNum(String childOrderNum) {
        this.childOrderNum = childOrderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Long shippingCost) {
        this.shippingCost = shippingCost;
    }

    public RetailGoodsSnapshot getRetailGoodsSnapshots() {
        return retailGoodsSnapshots;
    }

    public void setRetailGoodsSnapshots(RetailGoodsSnapshot retailGoodsSnapshots) {
        this.retailGoodsSnapshots = retailGoodsSnapshots;
    }
}
