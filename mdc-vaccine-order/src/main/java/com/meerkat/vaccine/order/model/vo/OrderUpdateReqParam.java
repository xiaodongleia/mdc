package com.meerkat.vaccine.order.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 订单返回类型
 *
 * @author zhujx
 * @date 2022/04/12 19:53
 */
@Data
public class OrderUpdateReqParam {

    /**
     * 记录id
     */
    private String requestId;

    /**
     * 清洗后数据
     */
    private List<OpsOrderVO> orderUpdateParamList;

    /**
     * 清洗后数据
     */
    private List<OpsRetailOrderVO> retailOrderUpdateParamList;

    public OrderUpdateReqParam(String requestId) {
        this.requestId = requestId;
    }

    public OrderUpdateReqParam() {
    }
}
