package com.meerkat.vaccine.order.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderExt {

    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 二方订单是否核销
     */
    private Integer secondOrderCheck;

    /**
     * 支付宝账号
     */
    private String receivingAccount;

    /**
     * 核销码
     */
    private String checkOffCode;

    /**
     * 订单标签，多个以#号分割
     */
    private List<String> orderTags;

    /**
     * 细分渠道
     */
    private String segmentChannel;

    private Integer liveOnLine;

    /**
     * 二方核销时间
     */
    private Date secondOrderCheckTime;


}