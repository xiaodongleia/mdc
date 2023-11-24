package com.meerkat.vaccine.order.service.impl;

import com.meerkat.vaccine.order.domain.MdcClientOrderDO;
import com.meerkat.vaccine.order.mapper.MdcClientOrderMapper;
import com.meerkat.vaccine.order.service.MdcClientOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户订单关联表 服务实现类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Service
public class MdcClientOrderServiceImpl extends ServiceImpl<MdcClientOrderMapper, MdcClientOrderDO> implements MdcClientOrderService {

}
