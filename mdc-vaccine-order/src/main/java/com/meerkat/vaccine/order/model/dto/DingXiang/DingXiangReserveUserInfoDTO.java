package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

/**
 *  客户信息
 *
 * @author zxw
 * @date 2022/03/02 18:27
 */
@Data
public class DingXiangReserveUserInfoDTO {

    //姓名
    private String name;

    //性别
    private Integer gender;

    //户籍地
    private String domicile;

    //联系电话
    private String contactPhoneNo;

    //生日
    private String birthday;

    //联系地址
    private String contactAddress;

    //身份证
    private String idCard;

    //年龄
    private String age;

    //详细地址
    private String detailAddress;
}
