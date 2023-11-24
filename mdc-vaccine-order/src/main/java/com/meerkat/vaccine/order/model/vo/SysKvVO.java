package com.meerkat.vaccine.order.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxw
 * @date 2023/03/10 15:40
 */
@Data
public class SysKvVO {

    @ApiModelProperty("键")
    private String k;

    @ApiModelProperty("备注")
    private String t;

    @ApiModelProperty("城市唯一标识")
    private String cityCode;
}
