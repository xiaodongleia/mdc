package com.meerkat.main.job;

import com.meerkat.vaccine.order.adapter.hander.BaiDuHandlerAdapter;
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
 * @date 2022/03/05 10:22
 */
@Slf4j
@Configuration
//@EnableScheduling
//@ConditionalOnProperty(prefix = "spring.scheduling.enable", name = "baidu-order-ods-job", havingValue = "true")
public class BaiDuDataToOdsJob {

    @Autowired
    private BaiDuHandlerAdapter baiDuHandlerAdapter;

    AtomicBoolean flag = new AtomicBoolean(true);

    //@Scheduled(cron = "${spring.scheduling.corn.baidu-order-ods-job}")
    @XxlJob("BaiDuDataToOdsJob")
    private void doJob1() {
        XxlJobHelper.log("BaiDuDataToOdsJob:begin");
        if (!flag.get()) {
            log.info("flag 上次任务尚未执行完毕");
            return;
        }
        flag.set(false);
        try {
            log.info("开始执行定时任务时间: BaiDuDataToOdsJob");
            baiDuHandlerAdapter.obtainData(SourceSystemEnum.BAI_DU.getName());
            log.info("结束执行定时任务时间: BaiDuDataToOdsJob");
        } catch (Exception e) {
            log.error("error:{}", e.getMessage(), e);
        } finally {
            flag.set(true);
        }
        XxlJobHelper.log("BaiDuDataToOdsJob:end");
    }
}
