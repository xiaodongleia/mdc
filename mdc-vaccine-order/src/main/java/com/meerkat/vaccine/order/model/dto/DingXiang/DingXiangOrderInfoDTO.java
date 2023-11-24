package com.meerkat.vaccine.order.model.dto.DingXiang;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zxw
 * @date 2022/03/02 17:58
 */
@Data
public class DingXiangOrderInfoDTO {

    @ApiModelProperty("订单号")
    private String orderSplitId;

    private String orderItemId;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("支付状态")
    private Integer payStatus;

    @ApiModelProperty("订单创建时间")
    private Date orderCreateTime;

    @ApiModelProperty("预约服务")
    private String serviceService;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("订单金额")
    private BigDecimal amount;
}
