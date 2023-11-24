package com.meerkat.notice.adapter.hander;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.notice.adapter.model.dto.OrderStatusDTO;
import com.meerkat.notice.adapter.model.enums.RobotNoticeEnum;
import org.springframework.stereotype.Service;

/**
 * 订单状态推送适配器
 *
 * @author zhujx
 * @date 2022/03/30 14:50
 */
@Service
public class OrderStatusNoticeAdapter extends RobotNoticeHandlerAdapter {

    @Override
    public Integer getType() {
        return RobotNoticeEnum.ORDER_STATUS.getCode();
    }

    @Override
    public void notice(String body) {
        OrderStatusDTO orderStatusDTO = JSONObject.parseObject(body, OrderStatusDTO.class);
        robotNoticeConfig.noticeOrderStatus(orderStatusDTO.getSourceName(), orderStatusDTO.getOrderCode(), orderStatusDTO.getOrderStatus());
    }


}
