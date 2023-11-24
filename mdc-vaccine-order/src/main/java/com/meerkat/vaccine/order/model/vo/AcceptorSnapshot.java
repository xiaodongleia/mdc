package com.meerkat.vaccine.order.model.vo;

import cn.hutool.core.util.IdcardUtil;
import lombok.Data;

/**
 *
 */
@Data
public class AcceptorSnapshot {

    /**
     * 履约人id
     */
    private Long acceptorId;
    /**
     * 身份证
     */
    private String acceptorIdCard;

    /**
     * 证件类型
     */
    private Integer credentialType;

    /**
     * 姓名
     */
    private String acceptorName;

    /**
     * 手机号
     */
    private String acceptorMobile;

    /**
     * 性别
     */
    private Integer gender;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 关系
     */
    private String relation;

    /**
     * 婚姻状态 0未婚  1已婚
     */
    private Integer marriageStatus;

    /**
     * 单位id
     */
    private Long organizationCompanyId;

    /**
     * 所在部门
     */
    private String department;

    /**
     * 员工号
     */
    private String employeeId;

    /**
     * 职级
     */
    private String position;

    /**
     * 退休，0:未退休 1:退休
     */
    private Integer isRetire;

    /**
     * 保健级别
     */
    private String healthLevel;

    /**
     * 保健号
     */
    private String healthNum;

    /**
     * 社保号
     */
    private String socialSecurity;

    /**
     * vip级别，0-非vip 1-vip
     */
    private Integer vipLevel;

    /**
     * 分组（分组套餐）
     */
    private String groupGoods;

    /**
     * 民族 关联tb_nation表
     */
    private String nationality;

    /**
     * 地址
     */
    private AcceptorAddress acceptorAddress;


    public Integer getGender() {
        if (this.acceptorIdCard != null && !"".equals(this.acceptorIdCard) && IdcardUtil.isValidCard(this.acceptorIdCard)) {
            return IdcardUtil.getGenderByIdCard(this.acceptorIdCard);
        }
        return gender;
    }


    @Data
    public static class AcceptorAddress {
        /**
         * 地址id
         */
        private Integer addressId;
        /**
         * 省
         */
        private String province;
        /**
         * 市
         */
        private String city;
        /**
         * 区
         */
        private String district;

        /**
         * 详细地址
         */
        private String detailsAddress;

        /**
         * 经度
         */
        private String latitude;
        /**
         * 纬度
         */
        private String longitude;


    }
}
