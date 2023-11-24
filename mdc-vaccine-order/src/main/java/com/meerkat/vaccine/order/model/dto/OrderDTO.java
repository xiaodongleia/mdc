package com.meerkat.vaccine.order.model.dto;

import lombok.Data;

/**
 * 拉取订单
 *
 * @author zhujx
 * @date 2022/02/22 14:14
 */
@Data
public class OrderDTO {

    private Boolean success;

    private Integer errorCode;

    private ResultDTO results;





}
