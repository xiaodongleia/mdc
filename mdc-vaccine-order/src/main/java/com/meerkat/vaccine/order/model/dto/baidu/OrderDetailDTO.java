package com.meerkat.vaccine.order.model.dto.baidu;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/03/04 15:10
 */
@Data
public class OrderDetailDTO {

    //错误码
    private Integer status;

    //数据
    private OrderDataDTO data;


}
