package com.meerkat.vaccine.order.mapper;

import com.meerkat.vaccine.order.domain.OdsExcelOrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 京东订单客户表格导入表 Mapper 接口
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Mapper
public interface OdsExcelOrderMapper extends BaseMapper<OdsExcelOrderDO> {

}
