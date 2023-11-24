package com.meerkat.vaccine.order.mapper;

import com.meerkat.vaccine.order.domain.MdcClientOrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户订单关联表 Mapper 接口
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Mapper
public interface MdcClientOrderMapper extends BaseMapper<MdcClientOrderDO> {

}
