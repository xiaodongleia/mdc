package com.meerkat.vaccine.order.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meerkat.common.utils.CommHttpUtils;
import com.meerkat.vaccine.order.model.vo.OpsOrderVO;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;
import com.meerkat.vaccine.order.service.MdcOrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxw
 * @date 2022/03/31 11:00
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "order.config.ops")
public class OrderToOpsConfig {

    @Autowired
    private MdcOrderService mdcOrderService;

    /**
     * 推送订单接口
     */
    private String url;

    public void orderToOps(List<OpsOrderVO> opsOrderVOS,String requestId) {

//        boolean success = false;
//
//        if (opsOrderVOS == null || opsOrderVOS.isEmpty()){
//            mdcOrderService.saveOrder(success,requestId);
//
//            return;
//        }

        OrderUpdateReqParam param = new OrderUpdateReqParam();
        param.setOrderUpdateParamList(opsOrderVOS);
        param.setRequestId(requestId);

//        String s = CommHttpUtils.doPostJson(url, JSON.toJSONString(param));
//        String result = JSONObject.parseObject(s).getString("message");
//
//
//        if (result.equals("success")){
//            success = true;
//        }
        String s = CommHttpUtils.doPostJson(url, JSON.toJSONString(param));
//        mdcOrderService.saveOrder(success,requestId);

    }
}
