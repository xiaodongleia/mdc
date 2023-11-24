package com.meerkat.vaccine.order.adapter;

import com.meerkat.vaccine.order.domain.MdcOrderDO;

import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;

import java.util.List;

/**
 * 渠道适配器
 *
 * @author zhujx
 * @date 2022/03/03 13:40
 */
public interface SourceAdapter {

    /**
     * 数据进入ods
     *
     * @param sourceName sourceName
     * @author zhujx
     * @date 2022/4/12 19:38
     */
    void obtainData(String sourceName);




    /**
     * 清洗订单
     *
     * @param sourceName sourceName
     * @return java.util.Map<java.lang.String, java.util.List < com.meerkat.vaccine.order.model.vo.OpsOrderVO>>
     * @author zhujx
     * @date 2022/4/12 19:38
     */
    OrderUpdateReqParam clearOrder(String sourceName);

    /**
     * 清洗客户
     *
     * @author zhujx
     * @date 2022/3/14 14:37
     */
    void clearClient();



    /**
     * 插入内存
     *
     * @param orderList orderList
     * @return java.util.Map<java.lang.String, java.util.List < com.meerkat.vaccine.order.model.vo.OpsOrderVO>>
     * @author zhujx
     * @date 2022/4/12 19:51
     */
    OrderUpdateReqParam putLocalMemory(List<MdcOrderDO> orderList);


}
