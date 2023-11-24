package com.meerkat.vaccine.order.model.vo;

import lombok.Data;


@Data
public class OperatorSnapshot {

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */

    private String mobile;

    /**
     * 出生年月
     */
    private String birthday;

    /**
     * 性别：0->未知；1->男；2->女
     */
    private int gender;


}
