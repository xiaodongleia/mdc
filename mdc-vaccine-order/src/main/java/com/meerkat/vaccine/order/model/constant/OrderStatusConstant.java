package com.meerkat.vaccine.order.model.constant;

import java.util.HashMap;

/**
 * 订单状态
 *
 * @author zhujx
 * @date 2022/02/22 15:17
 */
public class OrderStatusConstant {

    public final static HashMap<Integer, String> STATUS_MAP = new HashMap<>();

    static {
        STATUS_MAP.put(101, "待支付");
        STATUS_MAP.put(102, "待预约");
        STATUS_MAP.put(103, "待使用");
        STATUS_MAP.put(104, "交易完成");
        STATUS_MAP.put(105, "交易关闭");
        STATUS_MAP.put(106, "申请退款");
        STATUS_MAP.put(107, "退款成功");
        STATUS_MAP.put(108, "退款失败");
    }
}
