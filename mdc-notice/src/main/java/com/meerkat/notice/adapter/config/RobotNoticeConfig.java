package com.meerkat.notice.adapter.config;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.common.utils.CommHttpUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 机器人通知
 *
 * @author zhujx
 * @date 2022/02/22 14:59
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "order.config.robot")
public class RobotNoticeConfig {

    private String statusUrl;

    private String appointUrl;


    @Async("asyncAgentExecutorSingle")
    public void noticeOrderStatus(String sourceName, String orderCode, String status) {
        String content = "{\"msg_type\":\"post\",\"content\":{\"post\":{\"zh_cn\":{\"title\":\"订单状态提醒\",\"content\":[[{\"tag\":\"text\",\"text\":\"来自 %s 渠道的订单，订单编号为 %s 的状态变更为 %s,请及时处理\"}]]}}}}";
        String json = String.format(content, sourceName, orderCode, status);

        String result = CommHttpUtils.doPostJson(statusUrl, json);
        Integer statusCode = JSONObject.parseObject(result).getInteger("StatusCode");
        if (statusCode == null || statusCode != 0) {
            log.warn("{} 内容发送错误,原因为{}", orderCode, result);
        }
    }

    public void noticeOrderAppoint(String excelUrl) {
        String content = "{\"msg_type\":\"post\",\"content\":{\"post\":{\"zh_cn\":{\"title\":\"二、三针提醒计划\",\"content\":[[{\"tag\":\"text\",\"text\":\"明日二三针客户名单：\"},{\"tag\":\"a\",\"text\":\"请下载查看\",\"href\":\"%s\"}]]}}}}";

        String json = String.format(content, excelUrl);

        String result = CommHttpUtils.doPostJson(appointUrl, json);
        Integer statusCode = JSONObject.parseObject(result).getInteger("StatusCode");
        if (statusCode == null || statusCode != 0) {
            log.warn("{} 内容发送错误,原因为{}", excelUrl, result);
        }
    }

    @Async("asyncAgentExecutorSingle")
    public void noticeOrderException(String expectionMsg,String webhook) {
        String content = "{\"msg_type\":\"post\",\"content\":{\"post\":{\"zh_cn\":{\"title\":\"获取订单异常提醒\",\"content\":[[{\"tag\":\"text\",\"text\":\" %s \"}]]}}}}";
        String json = String.format(content, expectionMsg);

        String result = CommHttpUtils.doPostJson(webhook, json);
        Integer statusCode = JSONObject.parseObject(result).getInteger("StatusCode");
        if (statusCode == null || statusCode != 0) {
            log.warn("消息发送错误,原因为{}", result);
        }
    }


}
