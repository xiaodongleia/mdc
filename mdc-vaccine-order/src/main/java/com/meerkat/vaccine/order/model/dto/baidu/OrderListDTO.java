package com.meerkat.vaccine.order.model.dto.baidu;

import lombok.Data;

import java.util.List;

/**
 * @author zxw
 * @date 2022/03/04 16:24
 */
@Data
public class OrderListDTO {

    private Integer status;

    private List<OrderListRespDTO> data;
}
