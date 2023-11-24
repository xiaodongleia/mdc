package com.meerkat.vaccine.order.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 键值参数
 *
 * @author zhujx
 * @date 2022/03/11 10:04
 */
@Data
@ApiModel(value = "SysKvDO参数", description = "键值参数")
public class SysKvParam {


    @ApiModelProperty("键")
    private String k;

    @ApiModelProperty("值")
    private String v;

}
