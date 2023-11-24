package com.meerkat.vaccine.order.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {

    private PageBeanDTO pageBean;

    private List<ItemDTO> items;

}