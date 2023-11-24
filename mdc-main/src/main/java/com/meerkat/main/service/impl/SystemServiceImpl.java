package com.meerkat.main.service.impl;

import com.meerkat.common.service.SystemService;
import com.meerkat.notice.adapter.hander.OrderStatusNoticeAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * base 实现
 *
 * @author zhujx
 * @date 2022/04/15 14:40
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private OrderStatusNoticeAdapter orderStatusNoticeAdapter;

    @Override
    public void notice(String json) {
        orderStatusNoticeAdapter.notice(json);
    }
}
