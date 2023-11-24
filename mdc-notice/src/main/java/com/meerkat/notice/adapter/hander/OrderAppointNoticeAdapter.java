package com.meerkat.notice.adapter.hander;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.notice.adapter.model.dto.OrderAppointDTO;
import com.meerkat.notice.adapter.model.enums.RobotNoticeEnum;
import org.springframework.stereotype.Service;

/**
 * 订单状态推送适配器
 *
 * @author zhujx
 * @date 2022/03/30 14:50
 */
@Service
public class OrderAppointNoticeAdapter extends RobotNoticeHandlerAdapter {

    @Override
    public Integer getType() {
        return RobotNoticeEnum.ORDER_APPOINT.getCode();
    }

    @Override
    public void notice(String body) {
        OrderAppointDTO orderAppointDTO = JSONObject.parseObject(body, OrderAppointDTO.class);
        robotNoticeConfig.noticeOrderAppoint(orderAppointDTO.getExcelUrl());
    }


}
