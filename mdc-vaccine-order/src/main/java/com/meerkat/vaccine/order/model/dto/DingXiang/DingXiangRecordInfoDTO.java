package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

import java.util.Date;

/**
 * @author zxw
 * @date 2022/03/02 17:33
 */
@Data
public class DingXiangRecordInfoDTO {

    //预约id
    private String reserveOrderId;

    //预约创建时间
    private Date createTime;

    //预约日期
    private String reserveDate;

    //预约状态
    private Integer reserveState;
}
