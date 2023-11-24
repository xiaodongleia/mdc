package com.meerkat.notice.adapter.model.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 订单推送状态
 *
 * @author zhujx
 * @date 2022/03/30 14:57
 */
@Data
public class OrderStatusDTO {

    /**
     * 渠道名称
     */
    private String sourceName;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 订单状态(明文)
     */
    private String orderStatus;


    public OrderStatusDTO(String sourceName, String orderCode, String orderStatus) {
        this.sourceName = sourceName;
        this.orderCode = orderCode;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
