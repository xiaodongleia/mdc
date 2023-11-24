package com.meerkat.vaccine.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meerkat.vaccine.order.domain.MdcOrderAppointDO;

/**
 * <p>
 * 订单履约时间表 服务类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
public interface MdcOrderAppointService extends IService<MdcOrderAppointDO> {

    /**
     * 履约时间提醒
     *
     * @return java.lang.String
     * @author zhujx
     * @date 2022/4/13 11:27
     */
    String doAppointRemindJob();

}
