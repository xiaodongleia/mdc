package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 客户订单关联表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("mdc_client_order")
@ApiModel(value = "MdcClientOrderDO对象", description = "客户订单关联表")
public class MdcClientOrderDO implements Serializable {



    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户手机号")
    private String phoneNo;

    @ApiModelProperty("订单号")
    private String orderSplitId;

    @ApiModelProperty("子订单编号")
    private String childOrder;



}
