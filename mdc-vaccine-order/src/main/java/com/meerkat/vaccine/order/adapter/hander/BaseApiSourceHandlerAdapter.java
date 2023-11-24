package com.meerkat.vaccine.order.adapter.hander;

import com.meerkat.vaccine.order.config.OrderToOpsConfig;
import com.meerkat.vaccine.order.model.vo.OpsOrderVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 渠道API抓取导入适配器
 *
 * @author zhujx
 * @date 2022/03/03 13:40
 */
public abstract class BaseApiSourceHandlerAdapter extends BaseSourceHandlerAdapter {

    @Autowired
    protected OrderToOpsConfig toOpsConfig;

    /**
     * 渠道
     *
     * @return java.lang.String
     * @author zhujx
     * @date 2022/3/14 14:36
     */
    abstract String getSource();

    /**
     * 渠道名称
     *
     * @return java.lang.String
     * @author zhujx
     * @date 2022/3/14 14:36
     */
    abstract String getName();

    /**
     * 抓取sku信息
     *
     * @author zxw
     * @date 2022/4/11 2022/4/11
     */
    abstract void getSku();

    public boolean supports(String source) {
        return getSource().equals(source);
    }

    protected void orderToOps(List<OpsOrderVO> opsOrderVOS, String requestId) {
        toOpsConfig.orderToOps(opsOrderVOS, requestId);
    }

}
