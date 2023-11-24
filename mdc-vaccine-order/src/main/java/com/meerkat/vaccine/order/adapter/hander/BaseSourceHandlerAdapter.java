package com.meerkat.vaccine.order.adapter.hander;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meerkat.common.utils.DateUtils;
import com.meerkat.common.utils.NanoIdUtils;
import com.meerkat.vaccine.order.adapter.SourceAdapter;
import com.meerkat.vaccine.order.domain.MdcClientDO;
import com.meerkat.vaccine.order.domain.MdcOrderAppointDO;
import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;
import com.meerkat.vaccine.order.service.*;
import com.meerkat.vaccine.order.util.LocalMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 渠道适配器
 *
 * @author zhujx
 * @date 2022/03/03 13:40
 */
@Slf4j
public abstract class BaseSourceHandlerAdapter implements SourceAdapter {


    @Autowired
    protected MdcOrderService mdcOrderService;

    @Autowired
    protected MdcClientService mdcClientService;

    @Autowired
    protected MdcClientOrderService mdcClientOrderService;

    @Autowired
    protected MdcOrderAppointService mdcOrderAppointService;

    @Autowired
    protected MdcOrderToOpsService mdcOrderToOpsService;

    private static final String YI_LU = "医鹿";
    private final static List<String> YiLuList = new ArrayList<>() {
        {
            add("医鹿-医鹿");
            add("医鹿-支付宝");
            add("医鹿-微信");
            add("医鹿-其他");
        }
    };

    protected Long getDateTime() {
        return DateUtils.dateYmdLong(new Date());
    }


    @Override
    public OrderUpdateReqParam putLocalMemory(List<MdcOrderDO> orderList) {
        //        mdcOrderService.saveOrUpdateBatch(orderList);
        String requestId = NanoIdUtils.randomNanoId();
        LocalMemory.put(requestId, orderList);
        return new OrderUpdateReqParam(requestId);
    }

    /**
     * 获取数据库中订单数据
     *
     * @param sourceName 来源
     * @return java.util.HashMap<java.lang.String, com.meerkat.vaccine.order.domain.MdcOrderDO>
     * @author zhujx
     * @date 2022/4/8 15:39
     */
    protected HashMap<String, MdcOrderDO> getOldOrderMap(String sourceName) {
        HashMap<String, MdcOrderDO> oldOrderMap = new HashMap<>();
        //查询crm订单
        LambdaQueryWrapper<MdcOrderDO> mdcQuery = new LambdaQueryWrapper<>();

        if (!sourceName.equals(YI_LU)){
            mdcQuery.eq(MdcOrderDO::getIsDeleted, 0).in(MdcOrderDO::getSource, sourceName);
        }else {
            mdcQuery.eq(MdcOrderDO::getIsDeleted, 0).in(MdcOrderDO::getSource, YiLuList);
        }
        List<MdcOrderDO> oldOrderList = mdcOrderService.list(mdcQuery);

        oldOrderList.forEach(o -> oldOrderMap.put(o.getOrderSplitId(), o));
        return oldOrderMap;
    }

    /**
     * 获取数据库中履约人数据
     *
     * @param sourceName 来源
     * @return java.util.HashMap<java.lang.String, com.meerkat.vaccine.order.domain.MdcClientDO>
     * @author zhujx
     * @date 2022/4/8 15:39
     */
    protected HashMap<String, MdcClientDO> getOldClientMap(String sourceName) {
        HashMap<String, MdcClientDO> oldClientMap = new HashMap<>();
        //查询客户表数据
        LambdaQueryWrapper<MdcClientDO> queryClient = new LambdaQueryWrapper<>();
        if (!sourceName.equals(YI_LU)){
            queryClient.eq(MdcClientDO::getIsDeleted, 0).in(MdcClientDO::getSource, sourceName);
        }else {
            queryClient.eq(MdcClientDO::getIsDeleted, 0).in(MdcClientDO::getSource, YiLuList);
        }

        List<MdcClientDO> oldClientList = mdcClientService.list(queryClient);

        oldClientList.forEach(o -> oldClientMap.put(o.getPhoneNo(), o));
        return oldClientMap;
    }

    /**
     * 获取数据库中履约数据
     *
     * @param sourceName sourceName
     * @return java.util.HashMap<java.lang.String, com.meerkat.vaccine.order.domain.MdcOrderAppointDO>
     * @author zhujx
     * @date 2022/4/8 15:39
     */
    protected HashMap<String, MdcOrderAppointDO> getOldOrderAppointMap(String sourceName) {
        HashMap<String, MdcOrderAppointDO> oldOrderAppointMap = new HashMap<>();
        //查询订单预约信息
        LambdaQueryWrapper<MdcOrderAppointDO> appointQuery = new LambdaQueryWrapper<>();
        if (!sourceName.equals(YI_LU)){
            appointQuery.eq(MdcOrderAppointDO::getIsDeleted, 0).in(MdcOrderAppointDO::getSource, sourceName);
        }else {
            appointQuery.eq(MdcOrderAppointDO::getIsDeleted, 0).in(MdcOrderAppointDO::getSource, YiLuList);
        }

        List<MdcOrderAppointDO> oldOrderAppointList = mdcOrderAppointService.list(appointQuery);
        oldOrderAppointList.forEach(o -> oldOrderAppointMap.put(o.getOrderSplitId(), o));
        return oldOrderAppointMap;
    }


}
