package com.meerkat.vaccine.order.model.dto.DingXiang.sku;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/04/08 17:02
 */
@Data
public class DingXiangServiceInfoDTO {

    private Boolean success;

    private Integer errorCode;

    private DingXiangSkuResultDTO results;
}
