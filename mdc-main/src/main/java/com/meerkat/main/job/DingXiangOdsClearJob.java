package com.meerkat.main.job;

import com.meerkat.vaccine.order.adapter.hander.DingXiangHandlerAdapter;
import com.meerkat.vaccine.order.model.enums.SourceSystemEnum;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zxw
 * @date 2022/03/04 10:31
 */
@Slf4j
@Configuration
//@EnableScheduling
//@ConditionalOnProperty(prefix = "spring.scheduling.enable", name = "dingxiang-order-clear-job", havingValue = "true")
public class DingXiangOdsClearJob {

    @Autowired
    private DingXiangHandlerAdapter dingXiangHandlerAdapter;

    AtomicBoolean flag = new AtomicBoolean(true);

    //@Scheduled(cron = "${spring.scheduling.corn.dingxiang-order-clear-job}")
    @XxlJob("DingXiangOdsClearJob")
    private void doJob1() {
        XxlJobHelper.log("DingXiangOdsClearJob:begin");
        if (!flag.get()) {
            log.info("flag 上次任务尚未执行完毕");
            return;
        }

        flag.set(false);
        try {
            log.info("开始执行定时任务时间: 抓取丁香商品");
            dingXiangHandlerAdapter.getSku();
            log.info("开始执行定时任务时间: DingXiangOdsClear");
            dingXiangHandlerAdapter.clearClient();
            dingXiangHandlerAdapter.clearOrder(SourceSystemEnum.DING_XIANG.getName());
            log.info("结束执行定时任务时间: DingXiangOdsClear");
        } catch (Exception e) {
            log.error("error:{}",  e);
        } finally {
            flag.set(true);
        }
        XxlJobHelper.log("DingXiangOdsClearJob:end");
    }


}
