package com.meerkat.vaccine.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxw
 * @since 2022-04-11
 */
@Getter
@Setter
@TableName("mdc_goods")
@ApiModel(value = "MdcGoodsDO对象", description = "")
public class MdcGoodsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    @TableField("is_deleted")
    private Boolean deleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("skuId")
    private String skuId;

    @ApiModelProperty("来源渠道")
    private String source;


}
