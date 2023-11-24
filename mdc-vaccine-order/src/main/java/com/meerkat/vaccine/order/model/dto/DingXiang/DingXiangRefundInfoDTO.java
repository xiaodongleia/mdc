package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zxw
 * @date 2022/03/02 17:36
 */
@Data
public class DingXiangRefundInfoDTO {

    //退款状态
    private Integer refundStatus;

    //退款时间
    private Date refundTime;

    //退款金额
    private BigDecimal refundAmount;

    //退款操作人
    private String refundOperator;

    //退款类型
    private String type;

    //退款原因
    private String reason;
}
