package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

import java.util.List;

/**
 * @author zxw
 * @date 2022/03/02 17:29
 */
@Data
public class DingXiangOrderDTO {

    private DingXiangOrderInfoDTO orderInfo;

    private DingXiangUserSimpleInfoDTO userInfo;

    private List<DingXiangRecordInfoDTO> recordList;

    private DingXiangRefundInfoDTO refundInfo;
}
