package com.meerkat.vaccine.order.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jzy
 * @date 2022年03月10日 16:35
 * @description 企业微信-预约实体
 */
@Data
public class AppointmentVo {

    @ApiModelProperty(value="客户名称",name="name")
    private String name;

    @ApiModelProperty(value="预约人电话",name="phoneNo")
    private String phoneNo;

    @ApiModelProperty(value="身份证号",name="idCard")
    private String idCard;

    @ApiModelProperty(value="购买人电话",name="contactPhoneNo")
    private String contactPhoneNo;

    @ApiModelProperty(value="预约日期",name="firstAppointTime")
    private String firstAppointTime;

    @ApiModelProperty(value="预约日期二",name="secondAppointTime")
    private String secondAppointTime;

    @ApiModelProperty(value="预约日期三",name="thirdAppointTime")
    private String thirdAppointTime;

    @ApiModelProperty(value="沟通状态 待沟通、一针前已沟通、一针后已沟通",name="communicateStatus")
    private String communicateStatus;

}
