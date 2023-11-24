package com.meerkat.vaccine.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meerkat.common.service.SystemService;
import com.meerkat.common.utils.CopyUtil;
import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.mapper.MdcOrderMapper;
import com.meerkat.vaccine.order.model.constant.OrderStatusConstant;
import com.meerkat.vaccine.order.model.enums.OpsChannelEnum;
import com.meerkat.vaccine.order.model.enums.SourceSystemEnum;
import com.meerkat.vaccine.order.model.vo.MdcOrderVO;
import com.meerkat.vaccine.order.service.MdcOrderService;
import com.meerkat.vaccine.order.util.LocalMemory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Slf4j
@Service
public class MdcOrderServiceImpl extends ServiceImpl<MdcOrderMapper, MdcOrderDO> implements MdcOrderService {


    @Autowired
    private SystemService systemService;

    @Autowired
    private MdcOrderMapper mdcOrderMapper;


    private static final String YI_LU = "医鹿";
    private final static List<String> YiLuList = new ArrayList<>() {
        {
            add("医鹿-医鹿");
            add("医鹿-支付宝");
            add("医鹿-微信");
            add("医鹿-其他");
        }
    };
    @Override
    @Async("asyncAgentExecutor")
    public void saveOrder(boolean isSuccess, String requestId) {
        List<MdcOrderDO> mdcOrderDOS = LocalMemory.get(requestId);
        log.info("回调接口数据"+JSONObject.toJSONString(mdcOrderDOS));
        if (isSuccess) {

            try {
                for (MdcOrderDO mdcOrderDO : mdcOrderDOS) {
                    if (SourceSystemEnum.DING_XIANG.getName().equals(mdcOrderDO.getSource()) && mdcOrderDO.getIsNotice()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sourceName", mdcOrderDO.getSource());
                        jsonObject.put("orderCode", mdcOrderDO.getOrderSplitId());
                        jsonObject.put("orderStatus", OrderStatusConstant.STATUS_MAP.get(mdcOrderDO.getSelfOrderStatus()));
                        systemService.notice(jsonObject.toJSONString());
                    }
                }
            } catch (Exception e) {
                log.error("订单通知失败 " + e.getMessage(), e);
            }

            this.saveOrUpdateBatch(mdcOrderDOS);
            log.info("mdc_order 入库成功"+JSONObject.toJSONString(mdcOrderDOS));
        }
        LocalMemory.remove(requestId);
    }

    @Override
    public void saveExamWithinDays() {
        List<MdcOrderVO> orderInfoList = mdcOrderMapper.getOrderInfoList();

        for (MdcOrderVO mdcOrderVO : orderInfoList) {
            Long chorgaId = null;

            mdcOrderVO.setExamWithinDays(7);

            if (!YiLuList.contains(mdcOrderVO.getSource())){
                chorgaId = OpsChannelEnum.getIdByName(mdcOrderVO.getSource());
            }else {
                chorgaId = OpsChannelEnum.getIdByName(YI_LU);
            }
            if (chorgaId == 3 || chorgaId == 8){
                mdcOrderVO.setExamWithinDays(90);
                if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("24小时可约")){
                    mdcOrderVO.setExamWithinDays(1);
                }
                if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("7天内可约")){
                    mdcOrderVO.setExamWithinDays(7);
                }
                if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("15天内可约")){
                    mdcOrderVO.setExamWithinDays(15);
                }
                if (mdcOrderVO.getSkuName() != null && (mdcOrderVO.getSkuName().contains("30天内可约") || mdcOrderVO.getSkuName().contains("预计30天内可约"))){
                    mdcOrderVO.setExamWithinDays(30);
                }
                if (mdcOrderVO.getSkuName() != null && (mdcOrderVO.getSkuName().contains("60天内可约") || mdcOrderVO.getSkuName().contains("预计60天内可约"))){
                    mdcOrderVO.setExamWithinDays(60);
                }
                if (mdcOrderVO.getSkuName() != null && (mdcOrderVO.getSkuName().contains("90天内可约") || mdcOrderVO.getSkuName().contains("预计90天内可约"))){
                    mdcOrderVO.setExamWithinDays(90);
                }
            }

            if (chorgaId== 7){
                if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("天约不上必赔")){
                    mdcOrderVO.setExamWithinDays(90);
                }
            }

            try {
                if (chorgaId == 14){
                    if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("天未履约必赔")){
                        String skuName = mdcOrderVO.getSkuName();
                        String[] test1 = skuName.split("天未履约必赔");
                        List<String> stringList = Arrays.asList(test1);
                        for (String s : stringList) {
                            mdcOrderVO.setExamWithinDays(Integer.valueOf(s));
                        }
                    }
                }
            } catch (NumberFormatException e) {
                log.info("{}-解析可约时间异常:{}", mdcOrderVO.getSource(), mdcOrderVO.getOrderSplitId());
            }

            if (chorgaId == 2){
                if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("周末可约")){
                    mdcOrderVO.setExamWithinDays(7);
                }
            }

            if (chorgaId == 5){
                if (mdcOrderVO.getSkuName() != null && (mdcOrderVO.getSkuName().contains("天可约") || mdcOrderVO.getSkuName().contains("天开针"))){
                    mdcOrderVO.setExamWithinDays(90);
                }
            }

            if (mdcOrderVO.getSkuName() != null && mdcOrderVO.getSkuName().contains("2小时")){
                mdcOrderVO.setExamWithinDays(1);
            }

        }
        List<MdcOrderDO> mdcOrderDOS = CopyUtil.copyList(orderInfoList, MdcOrderDO.class);

        this.updateBatchById(mdcOrderDOS);
    }
}
