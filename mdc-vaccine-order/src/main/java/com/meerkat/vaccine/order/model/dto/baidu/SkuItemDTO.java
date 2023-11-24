package com.meerkat.vaccine.order.model.dto.baidu;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/03/04 17:04
 */
@Data
public class SkuItemDTO {

    private Integer status;

    private GoodsSkuDetailDTO data;
}
