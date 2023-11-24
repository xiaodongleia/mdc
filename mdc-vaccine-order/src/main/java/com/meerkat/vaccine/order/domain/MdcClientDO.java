package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("mdc_client")
@ApiModel(value = "MdcClientDO对象", description = "客户表")
public class MdcClientDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("来源")
    private String source;

    @ApiModelProperty("客户手机号")
    private String phoneNo;

    @ApiModelProperty("1：男，2：女")
    private Integer sex;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MdcClientDO clientDO = (MdcClientDO) o;
        return Objects.equals(name, clientDO.name) && Objects.equals(source, clientDO.source) && Objects.equals(phoneNo, clientDO.phoneNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, source, phoneNo);
    }

}
