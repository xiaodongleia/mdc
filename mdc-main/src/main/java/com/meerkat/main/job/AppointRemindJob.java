package com.meerkat.main.job;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.notice.adapter.hander.OrderAppointNoticeAdapter;
import com.meerkat.vaccine.order.service.MdcOrderAppointService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * 定时通知
 *
 * @author zhujx
 */
@Slf4j
@Configuration
//@EnableScheduling
//@ConditionalOnProperty(prefix = "spring.scheduling.enable", name = "appoint-remind-job", havingValue = "true")
public class AppointRemindJob {

    @Autowired
    private MdcOrderAppointService mdcOrderAppointService;
    @Autowired
    private OrderAppointNoticeAdapter orderAppointNoticeAdapter;


    //@Scheduled(cron = "${spring.scheduling.corn.appoint-remind-job}")
    @XxlJob("AppointRemindJob")
    private void doJob() {
        XxlJobHelper.log("AppointRemindJob:begin");
        log.info("开始执行定时任务时间: {}", new Date());
        String url = mdcOrderAppointService.doAppointRemindJob();
        if (StringUtils.isNotBlank(url)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("excelUrl", url);
            orderAppointNoticeAdapter.notice(jsonObject.toJSONString());
        }
        log.info("结束执行定时任务时间: {}", new Date());
        XxlJobHelper.log("AppointRemindJob:end");

    }


}