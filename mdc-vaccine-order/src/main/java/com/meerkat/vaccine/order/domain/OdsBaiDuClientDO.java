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
 * 百度客户表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("ods_bai_du_client")
@ApiModel(value = "OdsBaiDuClientDO对象", description = "百度客户表")
public class OdsBaiDuClientDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("数据时间")
    private Long dataTimeInt;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("客户姓名")
    private String name;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("出生日期")
    private String birthday;

    @ApiModelProperty("证件号")
    private String idCard;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("证件类型")
    private String cardType;


}
