package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

import java.util.Date;

/**
 *
 * 预约信息
 * @author zxw
 * @date 2022/03/02 18:22
 */
@Data
public class DingXiangReserveInfoDTO {

    //预约单号
    private String reserveOrderId;

    //预约服务
    private String reserveService;

    //规格
    private String specification;

    //预约状态
    private String reserveState;

    //预约单创建时间
    private Date createTime;

    //订单id
    private String orderSplitId;

    //预约日期
    private DingXiangReserveDateDTO reserveDateTime;
}
