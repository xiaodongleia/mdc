package com.meerkat.vaccine.order.model.constant;

import java.util.HashMap;

/**
 * @author zxw
 * @date 2022/03/05 16:22
 */
public class AllOrderStatusConstant {

    public final static HashMap<Integer, String> DINGXIANG_MAP = new HashMap<>();
    public final static HashMap<Integer, String> YILU_MAP = new HashMap<>();
    public final static HashMap<Integer, String> BAIDU_MAP = new HashMap<>();
    public final static HashMap<Integer, String> JD_MAP = new HashMap<>();
    public final static HashMap<Integer, String> ALIPAY = new HashMap<>();

    public final static HashMap<String, HashMap<Integer, String>> SOURCE_MAP = new HashMap<>();

    static {
//        DINGXIANG_MAP.put(101, "订单进行中");
        DINGXIANG_MAP.put(102, "待预约");
        DINGXIANG_MAP.put(103, "交易关闭");
        DINGXIANG_MAP.put(104, "交易完成");
        DINGXIANG_MAP.put(105, "退款中");
        DINGXIANG_MAP.put(106, "退款成功");

        YILU_MAP.put(101,"待支付");
        YILU_MAP.put(103,"交易关闭");
        YILU_MAP.put(102,"待预约");
        YILU_MAP.put(105,"退款中");
        YILU_MAP.put(106,"退款成功");
        YILU_MAP.put(104,"交易完成");
        YILU_MAP.put(107,"退款失败");

        ALIPAY.put(101,"待支付");
        ALIPAY.put(103,"交易关闭");
        ALIPAY.put(102,"待预约");
        ALIPAY.put(105,"退款中");
        ALIPAY.put(106,"退款成功");
        ALIPAY.put(104,"交易完成");
        ALIPAY.put(107,"退款失败");

        BAIDU_MAP.put(102,"待预约");
        BAIDU_MAP.put(104,"交易完成");
        BAIDU_MAP.put(105,"退款中");
        BAIDU_MAP.put(107,"退款失败");
        BAIDU_MAP.put(106,"退款成功");

        JD_MAP.put(102,"待预约");
        JD_MAP.put(108,"待付款");
        JD_MAP.put(103,"交易关闭");
        JD_MAP.put(106,"退款成功");
    }

    static {
        SOURCE_MAP.put("丁香园",DINGXIANG_MAP);
        SOURCE_MAP.put("医鹿",YILU_MAP);
        SOURCE_MAP.put("百度健康",BAIDU_MAP);
        SOURCE_MAP.put("京东",JD_MAP);
        SOURCE_MAP.put("支付宝",ALIPAY);
    }

    public static HashMap<Integer, String> getAllStatus(){
        HashMap<Integer, String> map =new HashMap<>(32);

        map.putAll(DINGXIANG_MAP);
        map.putAll(YILU_MAP);
        map.putAll(BAIDU_MAP);
        map.putAll(JD_MAP);
        map.putAll(ALIPAY);
        return map;

    }

    public static void main(String[] args) {
        HashMap<Integer, String> allStatus = getAllStatus();
        System.out.println(allStatus.get(102));
    }
}
