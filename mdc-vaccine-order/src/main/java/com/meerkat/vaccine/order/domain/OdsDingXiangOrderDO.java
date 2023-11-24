package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("ods_ding_xiang_order")
@ApiModel(value = "OdsDingXiangOrderDO对象", description = "订单详情")
public class OdsDingXiangOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("订单号")
    private String orderSplitId;

    private String orderItemId;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("支付状态")
    private Integer payStatus;

    @ApiModelProperty("订单创建时间")
    private Date orderCreateTime;

    @ApiModelProperty("预约服务")
    private String serviceService;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("订单金额")
    private BigDecimal amount;

    @ApiModelProperty("数据时间")
    private Long dataTimeInt;

    @ApiModelProperty("联系电话")
    private String contactPhoneNo;

    @ApiModelProperty("下单备注")
    private String orderRemarks;

    @ApiModelProperty("预约详情")
    private String reserveInfo;

    @ApiModelProperty("操作记录")
    private String operatingRecord;

    @ApiModelProperty("退款信息")
    private String refundInfo;

    @ApiModelProperty("规格id")
    private String specificationId;


}
