package com.meerkat.vaccine.order.service;


import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.model.vo.OpsOrderVO;

import java.util.List;
import java.util.Set;

/**
 * @author zxw
 * @date 2022/03/24 10:14
 */
public interface MdcOrderToOpsService {

    /**
     *  同步发生变更的订单
     * @param changeOrders changeOrders
     * @return java.util.List<com.meerkat.vaccine.order.model.vo.OpsOrderVO>
     * @author zxw
     * @date 2022/4/13 2022/4/13
     */
    List<OpsOrderVO> orderToOps(List<MdcOrderDO> changeOrders);

    /**
     *  同步发生变更的订单
     * @param changeOrders changeOrders
     * @return java.util.List<com.meerkat.vaccine.order.model.vo.OpsOrderVO>
     * @author zxw
     * @date 2022/4/13 2022/4/13
     */
    List<OpsOrderVO> orderToOpsByChild(List<MdcOrderDO> changeOrders);


    /**
     * 第一次全量同步
     *
     * @param
     * @return void
     * @author zxw
     * @date 2022/3/29 2022/3/29
     */
    void allOrderOfFirst();
}
