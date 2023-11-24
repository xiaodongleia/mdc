package com.meerkat.vaccine.order.adapter;

import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.domain.OdsExcelOrderDO;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;

import java.util.List;

/**
 * @author LENOVO
 * @version 1.0
 * @description: TODO
 * @date 2022/5/30 16:23
 */
public interface RetailSourceAdapter {

    /**
     * 数据进入ods
     *
     * @param sourceName sourceName
     * @author zhujx
     * @date 2022/4/12 19:38
     */
    List<OdsExcelOrderDO> obtainRetailData(String sourceName) throws Exception;


    /**
     * 插入内存
     *
     * @param orderList orderList
     * @return java.util.Map<java.lang.String, java.util.List < com.meerkat.vaccine.order.model.vo.OpsOrderVO>>
     * @author zhujx
     * @date 2022/4/12 19:51
     */
    OrderUpdateReqParam putLocalMemory(List<MdcOrderDO> orderList);


    /**
     * 清洗订单
     *
     * @param sourceData sourceData
     * @return java.util.Map<java.lang.String, java.util.List < com.meerkat.vaccine.order.model.vo.OpsOrderVO>>
     * @author zhujx
     * @date 2022/4/12 19:38
     */
    OrderUpdateReqParam clearOrder( List<OdsExcelOrderDO> sourceData,String sourceName);
}
