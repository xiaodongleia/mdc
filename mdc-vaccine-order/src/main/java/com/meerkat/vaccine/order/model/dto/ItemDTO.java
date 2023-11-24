package com.meerkat.vaccine.order.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ItemDTO {

    private String orderSplitId;

    private String orderItemId;

    private Date orderCreateTime;

    private String buyerPhoneNo;

    private String serviceSpecification;

    private Integer orderStatus;

    private Integer refundStatus;

    private Boolean waitReserve;
}