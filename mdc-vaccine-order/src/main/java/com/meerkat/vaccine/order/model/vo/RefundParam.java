package com.meerkat.vaccine.order.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @author zxw
 * @date 2022/04/20 16:08
 */
@Data
public class RefundParam {

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款时间
     */
    private Date refundTime;

    /**
     * 退款状态
     */
    private Integer refundState;

    /**
     * 退款金额
     */
    private Long refundAmount;

    private Date refundAuditTime;
}
