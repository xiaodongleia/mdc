package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/03/02 18:18
 */
@Data
public class DingXiangReserveItemDTO {

    private Boolean success;

    private int errorCode;

    private DingXiangReserveDTO results;
}
