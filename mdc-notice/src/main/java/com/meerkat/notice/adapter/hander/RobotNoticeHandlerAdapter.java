package com.meerkat.notice.adapter.hander;

import com.meerkat.notice.adapter.RobotNoticeAdapter;
import com.meerkat.notice.adapter.config.RobotNoticeConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 订单状态推送适配器
 *
 * @author zhujx
 * @date 2022/03/30 14:50
 */
public abstract class RobotNoticeHandlerAdapter implements RobotNoticeAdapter {

    @Autowired
    protected RobotNoticeConfig robotNoticeConfig;

    @Override
    public boolean supports(Integer type) {
        return Objects.equals(getType(), type);
    }
}
