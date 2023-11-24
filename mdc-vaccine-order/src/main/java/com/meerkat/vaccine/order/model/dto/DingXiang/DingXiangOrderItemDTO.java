package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/03/02 18:01
 */
@Data
public class DingXiangOrderItemDTO {

    private Boolean success;

    private int errorCode;

    private DingXiangOrderDTO results;
}
