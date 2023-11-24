package com.meerkat.vaccine.order.model.dto;

import lombok.Data;

@Data
public class PageBeanDTO {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalCount;

}