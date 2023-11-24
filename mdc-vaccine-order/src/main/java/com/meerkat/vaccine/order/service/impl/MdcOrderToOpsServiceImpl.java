package com.meerkat.vaccine.order.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.mapper.MdcOrderMapper;
import com.meerkat.vaccine.order.model.enums.OpsChannelEnum;
import com.meerkat.vaccine.order.model.enums.OpsOrderStatusEnum;
import com.meerkat.vaccine.order.model.enums.OpsRefundStatusEnum;
import com.meerkat.vaccine.order.model.vo.*;
import com.meerkat.vaccine.order.service.MdcOrderToOpsService;
import com.meerkat.vaccine.order.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2022/03/24 10:14
 */
@Service
public class MdcOrderToOpsServiceImpl implements MdcOrderToOpsService {

    private static final String pattern = "yyyy-MM-dd";

    private static final Logger LOG = LoggerFactory.getLogger(MdcOrderToOpsServiceImpl.class);

    @Autowired
    private MdcOrderMapper mcrmOrderMapper;

    private static final String YI_LU = "医鹿";
    private final static List<String> YiLuList = new ArrayList<>() {
        {
            add("医鹿-医鹿");
            add("医鹿-支付宝");
            add("医鹿-微信");
            add("医鹿-其他");
        }
    };

    private final static List<Long> CheckNames = new ArrayList<>() {
        {
            add(5L);
            add(7L);
            add(8L);
        }
    };

    @Override
    public List<OpsOrderVO> orderToOps(List<MdcOrderDO> changeOrders) {
        if (changeOrders.size() == 0) {
            return null;
        }
        List<String> orderSplitIds = changeOrders.stream().map(s -> s.getOrderSplitId()).collect(Collectors.toList());
        List<MdcOrderVO> allClient = mcrmOrderMapper.getAllOrderBySplitId(orderSplitIds);

        return buildData(allClient, changeOrders);
    }

    @Override
    public List<OpsOrderVO> orderToOpsByChild(List<MdcOrderDO> changeOrders) {
        if (changeOrders.size() == 0) {
            return null;
        }
        List<String> orderSplitIds = changeOrders.stream().map(s -> s.getChildOrder()).collect(Collectors.toList());
        List<MdcOrderVO> allClient = mcrmOrderMapper.getAllOrderByChildOrder(orderSplitIds);

        return buildData(allClient, changeOrders);

    }


    @Override
    public void allOrderOfFirst() {
        List<MdcOrderVO> allOrder = mcrmOrderMapper.getAllOrderBySplitId(null);

//        List<OpsOrderVO> opsOrderVOS = buildData(allOrder);
    }

    public List<OpsOrderVO> buildData(List<MdcOrderVO> allClient, List<MdcOrderDO> changeOrders) {
        List<OpsOrderVO> toOps = new ArrayList<>();
        Map<String, MdcOrderVO> orderMap = new HashMap<>();
        allClient.forEach(o -> orderMap.put(o.getOrderSplitId(), o));

        for (MdcOrderDO oneOrder : changeOrders) {
            OpsOrderVO opsOrder = new OpsOrderVO();
            RefundParam refundParam = new RefundParam();

            opsOrder.setNum(oneOrder.getNum());
            //判断订单状态和预约时间
            Integer orderStatus = oneOrder.getSelfOrderStatus();
            opsOrder.setRefundAmount(oneOrder.getRefundAmount() == null ? null : oneOrder.getRefundAmount().longValue());
            opsOrder.setSourceType(3);
            Integer opsRefundCode = OpsRefundStatusEnum.getOpsCodeBySelfCode(oneOrder.getSelfOrderStatus());
            Integer opsRefundParam = getOpsRefundCode(opsRefundCode, oneOrder.getRefundAmount(), oneOrder.getRefundTime(), orderStatus);
            opsOrder.setRefundState(opsRefundParam);
            if (OpsRefundStatusEnum.REFUND_SUC.getOpsCode().equals(opsRefundParam) || OpsRefundStatusEnum.REFUNDING.getOpsCode().equals(opsRefundParam)) {
                BigDecimal refundAmount = oneOrder.getRefundAmount();
                if (refundAmount == null) {
                    refundAmount = oneOrder.getOrderAmount();
                }
                refundParam.setRefundTime(oneOrder.getRefundTime());
                refundParam.setRefundAmount(refundAmount == null ? null : refundAmount.longValue());
            }
            refundParam.setRefundAuditTime(oneOrder.getRefundAuditTime());
            refundParam.setRefundState(opsRefundParam);
            refundParam.setRefundReason(oneOrder.getRefundReason());
            opsOrder.setRefundParam(refundParam);
            Integer opsOrderCode = OpsOrderStatusEnum.getOpsCodeBySelfCode(orderStatus);
            String firstAppointTime = oneOrder.getFirstAppointTime();


            Integer code = getOpsOrderCode(opsRefundCode, firstAppointTime, opsOrderCode);
            opsOrder.setState(code);
            opsOrder.setTotalAmount(oneOrder.getOrderAmount() == null || oneOrder.getOrderAmount().compareTo(BigDecimal.ZERO) == 0 ? oneOrder.getGoodsPrice().longValue() : oneOrder.getOrderAmount().longValue());
            if (OpsOrderStatusEnum.WAIT_PAY.getOpsCode().equals(code)) {
                opsOrder.setPayAmount(0L);
            } else {
                opsOrder.setPayAmount(oneOrder.getOrderAmount() == null ? null : oneOrder.getOrderAmount().longValue());
            }
            if (OpsOrderStatusEnum.CLOSE.getOpsCode().equals(code) && (opsOrder.getPayAmount() == null || opsOrder.getPayAmount() == 0L)) {
                opsOrder.setState(8);
            }
            opsOrder.setOrderType(0);
            opsOrder.setChorgaType(2);
            opsOrder.setBizCode("vaccine");
            opsOrder.setInvoiceType(0);
            opsOrder.setSkuName(oneOrder.getSkuName());
            opsOrder.setPaymentTime(oneOrder.getPayTime());
            opsOrder.setAcceptorName(oneOrder.getUserName());
            opsOrder.setAcceptorIdcard(oneOrder.getCardId());
            opsOrder.setAcceptorMobile(oneOrder.getContactPhoneNo());
            opsOrder.setOutOrderNum(oneOrder.getOrderSplitId());
            opsOrder.setGmtCreated(oneOrder.getOrderCreateTime());

            opsOrder.setBalance(oneOrder.getBalance());
            opsOrder.setDeposit(oneOrder.getDeposit());
            opsOrder.setDepositPayTime(oneOrder.getDepositPayTime());
            if (oneOrder.getIsPresale() != null) {
                opsOrder.setIsPresale("是".equals(oneOrder.getIsPresale()));
            } else {
                opsOrder.setIsPresale(false);
            }

            OrderExt orderExt = new OrderExt();
            if (!YiLuList.contains(oneOrder.getSource())) {
                opsOrder.setChorgaId(OpsChannelEnum.getIdByName(oneOrder.getSource()));
            } else {
                opsOrder.setChorgaId(OpsChannelEnum.getIdByName(YI_LU));
                orderExt.setSegmentChannel(oneOrder.getSource());
            }

            String skuCode = oneOrder.getSkuCode();
            if (OpsChannelEnum.JING_DONG.getName().equals(oneOrder.getSource()) && skuCode.length() > 8){
                skuCode = oneOrder.getSkuCode().substring(0, 8);
            }
            opsOrder.setSkuCode(skuCode);
            Date examDate = oneOrder.getExamDate();
            opsOrder.setExamDate(examDate);
            opsOrder.setChildOrder(oneOrder.getChildOrder());
            OperatorSnapshot operatorSnapshot = new OperatorSnapshot();
            operatorSnapshot.setMobile(oneOrder.getContactPhoneNo());
            operatorSnapshot.setName(oneOrder.getUserName());
            operatorSnapshot.setIdCard(oneOrder.getCardId());
            opsOrder.setOperatorSnapshot(operatorSnapshot);
            //System.out.println(oneOrder.getOrderSplitId());
            orderExt.setSecondOrderCheck(opsOrder.getState().equals(5) ? 1 : 0);
            orderExt.setSecondOrderCheckTime(oneOrder.getSecondOrderCheckTime());
            ArrayList<String> strings = new ArrayList<>();
            String orderTags = oneOrder.getOrderTags();
            if (!StringUtils.isBlank(orderTags)) {
                String[] split = orderTags.split(",");
                if (split.length > 1) {
                    for (int i = 0; i < split.length; i++) {
                        String s = split[i];
                        strings.add(s);
                    }
                    orderExt.setOrderTags(strings);
                    opsOrder.setOrderExt(orderExt);
                } else {
                    strings.add(orderTags);
                    orderExt.setOrderTags(strings);
                    opsOrder.setOrderExt(orderExt);
                }
            }
            opsOrder.setOrderExt(orderExt);
            opsOrder.setChildOrderNum(opsOrder.getOutOrderNum());
            AcceptorSnapshot acceptorSnapshot = new AcceptorSnapshot();
            AcceptorSnapshot.AcceptorAddress acceptorAddress = new AcceptorSnapshot.AcceptorAddress();
            acceptorSnapshot.setAcceptorAddress(acceptorAddress);
            acceptorSnapshot.setAcceptorMobile(opsOrder.getAcceptorMobile());
            acceptorSnapshot.setAcceptorName(opsOrder.getAcceptorName());
            acceptorSnapshot.setAcceptorIdCard(opsOrder.getAcceptorIdcard());
            opsOrder.setAcceptorSnapshot(acceptorSnapshot);
            GoodsSecondPartyParam goodsSecondPartyParam = new GoodsSecondPartyParam();
//            String skuCode = opsOrder.getSkuCode();
            goodsSecondPartyParam.setSkuCode(skuCode);
            goodsSecondPartyParam.setPurchaseQuantity(opsOrder.getNum());
            opsOrder.setGoodsSecondPartyParam(goodsSecondPartyParam);
            opsOrder.setShopId(oneOrder.getShopId());
            opsOrder.setShopName(oneOrder.getShopName());
            if (opsOrder.getState().equals(5)) {
                if (opsOrder.getChorgaId() == 3 || opsOrder.getChorgaId() == 5 || opsOrder.getChorgaId() == 8 || opsOrder.getChorgaId() == 7) {
                    orderExt.setSecondOrderCheck(0);
                    opsOrder.setOrderExt(orderExt);
                }
            }

            //预售订单 待支付  添加支付金额
            if (opsOrder.getIsPresale()) {
                if (opsOrder.getState().equals(0)) {
                    opsOrder.setPayAmount(oneOrder.getOrderAmount() == null ? null : oneOrder.getOrderAmount().longValue());
                }
            }


            MdcOrderVO orderVO = orderMap.get(opsOrder.getOutOrderNum());
            //京东 拼多多 天猫  不保存履约人名称
            if (CheckNames.contains(opsOrder.getChorgaId())) {
                if (Objects.isNull(orderVO)) {
                    opsOrder.setAcceptorName(null);
                    opsOrder.getAcceptorSnapshot().setAcceptorName(null);
                } else {
                    opsOrder.setAcceptorName(null);
                    opsOrder.setAcceptorMobile(null);
                    opsOrder.setAcceptorIdcard(null);
                    opsOrder.setAcceptorSnapshot(null);
                }
            }

            //扩龄订单备注
            String skuName = oneOrder.getSkuName();
            if (!StringUtils.isBlank(skuName)) {
                if (skuName.contains("45")) {
                    opsOrder.setOperateRemark("扩龄订单");
                }
            }
            if (!StringUtils.isBlank(oneOrder.getOperateRemark())) {
                if (!StringUtils.isBlank(opsOrder.getOperateRemark())) {
                    String operateRemark = opsOrder.getOperateRemark() + "；" + oneOrder.getOperateRemark();
                    opsOrder.setOperateRemark(operateRemark);
                } else {
                    opsOrder.setOperateRemark(oneOrder.getOperateRemark());
                }
            }


            opsOrder.setExamWithinDays(oneOrder.getExamWithinDays());
            toOps.add(opsOrder);
            LOG.info("mdc清洗后数据：{}", JSONUtil.toJsonStr(opsOrder));

        }
        return toOps;
    }

    public static Integer getOpsOrderCode(Integer opsRefundCode, String firstAppointTime, Integer opsOrderCode) {
        Integer code = null;
        if (null == opsRefundCode) {
            code = opsOrderCode;
        } else if (opsRefundCode.equals(OpsRefundStatusEnum.REFUNDING.getOpsCode()) || opsRefundCode.equals(OpsRefundStatusEnum.REFUND_FAIL.getOpsCode())) {
            if (firstAppointTime != null) {
                code = OpsOrderStatusEnum.WAIT_PAY_SUC.getOpsCode();
            } else {
                code = OpsOrderStatusEnum.ALREADY.getOpsCode();
            }
        } else if (opsRefundCode.equals(OpsRefundStatusEnum.REFUND_SUC.getOpsCode())) {
            code = OpsOrderStatusEnum.CLOSE.getOpsCode();
        }
        return code;
    }

    public static Integer getOpsRefundCode(Integer opsRefundCode, BigDecimal refundAmount, Date refundTime, Integer orderStatus) {
        if (opsRefundCode == null && (refundAmount != null || refundTime != null)) {
            if (orderStatus.equals(OpsOrderStatusEnum.CLOSE.getSelfCode())) {
                opsRefundCode = OpsRefundStatusEnum.REFUND_SUC.getOpsCode();
            }
        }
        return opsRefundCode;
    }

    public static Map<String, MdcOrderVO> clientListToMap(List<MdcOrderVO> allClient) {
        Map<String, MdcOrderVO> clientMap = new HashMap<>();
        allClient.forEach(o -> clientMap.put(o.getOrderSplitId(), o));
        return clientMap;
    }

}
