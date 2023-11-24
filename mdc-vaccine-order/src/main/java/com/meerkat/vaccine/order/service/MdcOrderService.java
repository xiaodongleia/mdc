package com.meerkat.vaccine.order.service;

import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
public interface MdcOrderService extends IService<MdcOrderDO> {

    void saveOrder(boolean isSuccess,String requestId);

    void saveExamWithinDays();

}
