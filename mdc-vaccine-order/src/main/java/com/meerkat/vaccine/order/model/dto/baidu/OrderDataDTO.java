package com.meerkat.vaccine.order.model.dto.baidu;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zxw
 * @date 2022/03/04 15:12
 */
@Data
public class OrderDataDTO {

    private Long newStoreId;

    //商家同意退款时间(ms级)
    private Date agreeRefundTime;

    //申请退款原因
    private String applyRefundReason;

    //申请退款时间 (ms级)
    private Date applyRefundTime;

    //预约日期（最新）
    private String appointDate;

    //联系方式
    private String appointMobile;

    //预约人
    private String appointUserName;

    //医师姓名
    private String assignName;

    //退款金额
    private Long backMoneyFen;

    //创建时间(ms级)
    private Date createTime;

    //商品信息
    private List<GoodsDTO> goods;

    //	商品总价格
    private Long goodsPriceFen;

    //用户留言
    private String message;

    //	订单编号
    private String orderId;

    //订单原价(分)
    private Long originalPriceFen;

    //商家结算金额(分)
    private Long paidAmountFen;

    //平台补贴(分)
    private Long bdDiscount;

    //用户实际支付金额（分）
    private Long userPayment;

    //付款时间(ms级)
    private Date payTime;

    //支付方式
    private Integer payType;

    //收货/购买人性别
    private String sex;

    //收货/购买人手机号
    private String receMobile;

    //收货/购买人
    private String receName;

    //订单状态
    private Integer status;

    //接种人信息（疫苗）
    private ClientDTO vaccinatedPerson;
}
