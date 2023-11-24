package com.meerkat.vaccine.order.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zxw
 * @date 2022/03/28 17:20
 */
@Data
public class OpsOrderVO {

    @ApiModelProperty("机构id")
    private Long organizationId;

    @ApiModelProperty("订单总金额")
    private Long totalAmount;

    @ApiModelProperty("应付金额（实际支付金额）")
    private Long payAmount;

    @ApiModelProperty("退款金额")
    private Long refundAmount;

    @ApiModelProperty("订单来源：0 微信小程序")
    private Integer sourceType;

    @ApiModelProperty("订单状态 0=待支付 1 =支付中 2=已支付 3=已关闭 4=已预约 5=已完成 6=预约失败 7=预约中")
    private Integer state;

    @ApiModelProperty("订单类型：0 服务订单 1 实物订单 2 充值订单")
    private Integer orderType;

    @ApiModelProperty("类型")
    private Integer chorgaType;

    @ApiModelProperty("订单业务类型 疫苗 或 实物")
    private String bizCode;

    @ApiModelProperty("发票类型：0->不开发票；1->电子发票；2->纸质发票")
    private Integer invoiceType;

    @ApiModelProperty("支付时间")
    private Date paymentTime;

    @ApiModelProperty("履约人姓名")
    private String acceptorName;

    @ApiModelProperty("履约人身份证号")
    private String acceptorIdcard;

    @ApiModelProperty("履约人手机号")
    private String acceptorMobile;

    @ApiModelProperty("外部id")
    private String outOrderNum;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("退款原因")
    private String refundReason;

    @ApiModelProperty("履约服务商")
    private String agreementMerchant;

    @ApiModelProperty("退款时间")
    private Date refundTime;

    @ApiModelProperty("联系电话")
    private String contactPhoneNo;

    @ApiModelProperty("退款状态：1 退款中 2 已退款 3 拒绝退款")
    private Integer refundState;

    @ApiModelProperty("渠道id")
    private Long chorgaId;

    @ApiModelProperty("sku唯一标识")
    private String skuCode;

    @ApiModelProperty("服务时间")
    private Date examDate;


    @ApiModelProperty("服务时间")
    private RefundParam refundParam;

    /**
     * 下单人信息
     */
    private OperatorSnapshot operatorSnapshot;

    /**
     * 订单其他字段
     */
    private OrderExt orderExt;

    private List<RetailGoodsSnapshot> goodsSnapshot;

    @ApiModelProperty("子订单编号")
    private String childOrder;

    @ApiModelProperty("下单人")
    private String single;

    @ApiModelProperty("下单时间")
    private Date singleTime;

    @ApiModelProperty("买家留言")
    private String remarkInfo;

    @ApiModelProperty("运费")
    private Long shippingCost;

    @ApiModelProperty("订单商品数量")
    private Integer num;

    @ApiModelProperty("子订单编号")
    private String childOrderNum;

    @ApiModelProperty("是否预售  否 FALSE 是 TRUE")
    private Boolean isPresale;

    @ApiModelProperty("定金金额")
    private BigDecimal deposit;

    @ApiModelProperty("尾款金额")
    private BigDecimal balance;

    @ApiModelProperty("定金支付时间")
    private Date depositPayTime;

    /**
     * 履约人信息
     */
    private AcceptorSnapshot acceptorSnapshot;


    private GoodsSecondPartyParam goodsSecondPartyParam;

    @ApiModelProperty("订单备注")
    private String operateRemark;

    @ApiModelProperty("时间标签")
    private Integer examWithinDays;


    @ApiModelProperty("店铺Id")
    private Long shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("sku名称")
    private String skuName;

}
