package com.meerkat.vaccine.order.model.dto.DingXiang.sku;

import lombok.Data;

import java.util.List;

/**
 * @author zxw
 * @date 2022/04/11 10:38
 */
@Data
public class DingXiangItemInfoDTO {

    private String commodityId;

    private String serviceName;

    private List<DingXiangSkuListDTO> skuList;
}
