package com.meerkat.vaccine.order.model.dto.baidu;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/03/04 15:28
 */
@Data
public class ClientDTO {

    //接种人姓名
    private String name;

    //接种人生日
    private String birthday;

    //	证件类型
    private String cardType;

    //接种人性别
    private String sex;

    //接种人电话
    private String phone;

    //证件号码
    private String idCard;
}
