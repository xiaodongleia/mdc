package com.meerkat.vaccine.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.model.excel.AppointRemindExcel;
import com.meerkat.vaccine.order.model.vo.MdcOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Mapper
public interface MdcOrderMapper extends BaseMapper<MdcOrderDO> {

    /**
     * 根据第三方订单编号查询订单
     *
     * @param orderSplitIds orderSplitIds
     * @return java.util.List<com.soul.rat.model.vo.McrmOrderVO>
     * @author zxw
     * @date 2022/3/28 2022/3/28
     */
    List<MdcOrderVO> getAllOrderBySplitId(@Param("orderSplitIds") List<String> orderSplitIds);


    /**
     * 查询全部订单
     *
     * @return java.util.List<com.soul.rat.model.vo.McrmOrderVO>
     * @author zxw
     * @date 2022/3/28 2022/3/28
     */
    List<MdcOrderVO> getOrderInfoList();


    /**
     * 根据第三方订单编号查询订单
     *
     * @param orderSplitIds orderSplitIds
     * @return java.util.List<com.soul.rat.model.vo.McrmOrderVO>
     * @author zxw
     * @date 2022/3/28 2022/3/28
     */
    List<MdcOrderVO> getOrderInfoBySplitId(@Param("orderSplitIds") List<String> orderSplitIds);

    /**
     * 根据子订单单编号查询订单
     *
     * @param orderSplitIds orderSplitIds
     * @return java.util.List<com.soul.rat.model.vo.McrmOrderVO>
     * @author zxw
     * @date 2022/3/28 2022/3/28
     */
    List<MdcOrderVO> getAllOrderByChildOrder(@Param("orderSplitIds") List<String> orderSplitIds);

    /**
     * 根据订单id获取履约时间
     *
     * @param orderIds orderIds
     * @return java.util.List<com.meerkat.vaccine.order.model.excel.AppointRemindExcel>
     * @author zhujx
     * @date 2022/4/13 11:28
     */
    List<AppointRemindExcel> listAppointRemindExcel(@Param("orderIds") HashSet<String> orderIds);
}
