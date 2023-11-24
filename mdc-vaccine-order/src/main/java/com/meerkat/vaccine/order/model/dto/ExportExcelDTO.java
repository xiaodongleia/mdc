package com.meerkat.vaccine.order.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zxw
 * @date 2022/03/03 19:38
 */
@Data
public class ExportExcelDTO {

    //渠道
    private String source;

    //开始时间
    private String startDate;

    //结束时间
    private String endDate;

    //订单状态
    private Integer orderStatus;

    //所导出的字段
    private List<String> codes;
}
