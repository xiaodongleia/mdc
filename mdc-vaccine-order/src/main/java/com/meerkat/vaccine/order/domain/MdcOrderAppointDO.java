package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单履约时间表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("mdc_order_appoint")
@ApiModel(value = "MdcOrderAppointDO对象", description = "订单履约时间表")
public class MdcOrderAppointDO implements Serializable {

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

    @ApiModelProperty("第一针预约时间")
    private String firstAppointTime;

    @ApiModelProperty("第二针预约时间")
    private String secondAppointTime;

    @ApiModelProperty("第三针预约时间")
    private String thirdAppointTime;

    @ApiModelProperty("渠道")
    private String source;

    @ApiModelProperty("子订单编号")
    private String childOrder;
}
