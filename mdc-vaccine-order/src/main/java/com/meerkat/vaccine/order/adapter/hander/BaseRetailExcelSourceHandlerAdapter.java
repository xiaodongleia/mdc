package com.meerkat.vaccine.order.adapter.hander;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meerkat.common.exception.BizException;
import com.meerkat.vaccine.order.domain.*;
import com.meerkat.vaccine.order.handel.FileHandler;
import com.meerkat.vaccine.order.model.enums.*;
import com.meerkat.vaccine.order.model.vo.*;
import com.meerkat.vaccine.order.service.OdsExcelOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wujs
 * @version 1.0
 * @description: 实物订单导入
 * @date 2022/5/30 15:22
 */
@Slf4j
@Service
public class BaseRetailExcelSourceHandlerAdapter extends BaseRetailSourceHandlerAdapter {
    @Autowired
    private OdsExcelOrderService odsExcelOrderService;

    @Override
    public List<OdsExcelOrderDO> obtainRetailData(String sourceName) throws Exception {
        Long dateYmdLong = getDateTime();

        List<OdsExcelOrderDO> list = getImportOrderDataList(dateYmdLong, sourceName);

        LambdaQueryWrapper<OdsExcelOrderDO> queryOrder = new LambdaQueryWrapper<>();
        queryOrder.eq(OdsExcelOrderDO::getDataTimeInt, dateYmdLong).eq(OdsExcelOrderDO::getOrderChannel, sourceName);
        odsExcelOrderService.remove(queryOrder);
        list.stream().forEach(it -> it.setBizCode("retail"));
        list.stream().forEach(it -> it.setRefundState(OrderRefundStateEnum.getByCode(it.getRefundStatenName())));
        list.stream().forEach(it -> it.setTpSkuCode(it.getSkuCode()));
        list.stream().forEach(it -> it.setPhoneNo(it.getConsigneePhoneNo()));
        odsExcelOrderService.saveBatch(list);
        return list;
    }

    @Override
    public OrderUpdateReqParam clearOrder(List<OdsExcelOrderDO> sourceData, String sourceName) {

        return toMcrmOrderAndClient(sourceData, sourceName);
    }

    /**
     * 读取文件
     *
     * @param dateYmdLong dateYmdLong
     * @param sourceName  sourceName
     * @return java.util.List<com.meerkat.vaccine.order.domain.OdsExcelOrderDO>
     * @author zhujx
     * @date 2022/4/8 15:51
     */
    private List<OdsExcelOrderDO> getImportOrderDataList(Long dateYmdLong, String sourceName) throws Exception {

        MultipartFile file = FileHandler.get();

        if (file == null) {
            throw new BizException("-1000", "文件不存在");
        }

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(0);
        params.setSheetNum(1);

        List<OdsExcelOrderDO> list;

        list = ExcelImportUtil.importExcel(file.getInputStream(), OdsExcelOrderDO.class, params);

        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("-1000", "文件不存在");
        }
        List<OdsExcelOrderDO> collect = list.stream().filter(it -> it.getOrderCode() != null).collect(Collectors.toList());
        for (OdsExcelOrderDO odsExcelOrderDO : collect) {
            if (StringUtils.isBlank(odsExcelOrderDO.getOrderChannel())) {
                throw new BizException("-500", "必填参数不存在");
            }
            if (odsExcelOrderDO.getPayment() == null || StringUtils.isBlank(odsExcelOrderDO.getSingle()) || StringUtils.isBlank(odsExcelOrderDO.getSkuCode()) || odsExcelOrderDO.getGoodsCount() == null || StringUtils.isBlank(odsExcelOrderDO.getOrderStatus()) || StringUtils.isBlank(odsExcelOrderDO.getOrderCode())) {
                throw new BizException("-500", "必填参数不存在");
            }
        }
        try {

            collect = collect.stream().filter(s -> StringUtils.isNotBlank(s.getOrderCode()) && sourceName.equals(s.getOrderChannel())).peek(s -> s.setDataTimeInt(dateYmdLong)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BizException("-1000", e.getMessage());
        }
        if (collect.isEmpty()) {
            throw new BizException("-1000", "上传失败，请查看订单渠道映射是否正确");
        }
        return collect;
    }

    /**
     * 转换数据
     *
     * @param list       list
     * @param sourceName sourceName
     * @return java.util.List<com.meerkat.vaccine.order.model.vo.OpsOrderVO>
     * @author zhujx
     * @date 2022/4/8 15:52
     */
    protected OrderUpdateReqParam toMcrmOrderAndClient(List<OdsExcelOrderDO> list, String sourceName) {

        HashMap<String, MdcOrderDO> oldOrderMap = getOldOrderMap(sourceName);
        HashMap<String, MdcClientDO> oldClientMap = getOldClientMap(sourceName);
        HashMap<String, MdcOrderAppointDO> oldOrderAppointMap = getOldOrderAppointMap(sourceName);
        List<MdcOrderDO> orderList = new ArrayList<>();
        HashSet<MdcClientDO> clientList = new HashSet<>();
        List<MdcOrderAppointDO> orderAppointList = new ArrayList<>();
        List<String> orderCodes = new ArrayList<>();
        List<MdcClientOrderDO> mcrmClientOrderList = new ArrayList<>();
        List<MdcOrderDO> changeOrders = new ArrayList<>();
        HashMap<String, OdsExcelOrderDO> objectHashMap = new HashMap<>(16);

        //导入的数据
        //发生变更的订单
        for (OdsExcelOrderDO odsOrder : list) {

            //提取订单信息
            extractedOrder(oldOrderMap, orderList, odsOrder, changeOrders, sourceName);
            orderCodes.add(odsOrder.getChildOrder());
            //提取客户信息
            extractedClient(oldClientMap, clientList, mcrmClientOrderList, odsOrder);
            //提取预约信息
            extractedOrderAppoint(oldOrderAppointMap, orderAppointList, odsOrder);
            objectHashMap.put(odsOrder.getOrderCode() + "-" + odsOrder.getSkuCode(), odsOrder);
        }
        OrderUpdateReqParam reqParam = new OrderUpdateReqParam();
        OrderUpdateReqParam requestVO = putLocalMemory(orderList);
        log.info("{}-requestId:{}", sourceName, requestVO.getRequestId());
        mdcClientService.saveOrUpdateBatch(clientList);
        //删除中间表关联关系
        if (!orderCodes.isEmpty()) {
            LambdaQueryWrapper<MdcClientOrderDO> query = new LambdaQueryWrapper<>();
            query.in(MdcClientOrderDO::getChildOrder, orderCodes);
            mdcClientOrderService.remove(query);
        }
        mdcClientOrderService.saveBatch(mcrmClientOrderList);
        mdcOrderAppointService.saveOrUpdateBatch(orderAppointList);
        List<OpsOrderVO> opsOrderList = mdcOrderToOpsService.orderToOpsByChild(changeOrders);
        if (CollectionUtils.isNotEmpty(opsOrderList)) {
            List<OpsRetailOrderVO> objects = new ArrayList<>();

            for (OpsOrderVO opsOrderVO : opsOrderList) {
                OpsRetailOrderVO opsRetailOrderVO = new OpsRetailOrderVO();
                String outOrderNum = opsOrderVO.getOutOrderNum();
                OdsExcelOrderDO odsExcelOrderDO = objectHashMap.get(opsOrderVO.getChildOrder());
                Integer codeByNameAndSourceName = OrderStatusEnum.getCodeByNameAndSourceName(odsExcelOrderDO.getOrderChannel(), odsExcelOrderDO.getOrderStatus());
                Integer integer = OrderStatusEnum.getmyCodeByCode(codeByNameAndSourceName);
                opsRetailOrderVO.setState(OpsOrderStatusEnum.getOpsCodeBySelfCode(integer));
                opsRetailOrderVO.setOutOrderNum(outOrderNum);
                opsRetailOrderVO.setChildOrderNum(odsExcelOrderDO.getOrderCode() + "-" + odsExcelOrderDO.getSkuCode());
                opsRetailOrderVO.setDiscountAmount(odsExcelOrderDO.getDiscountAmount() == null ? null : odsExcelOrderDO.getDiscountAmount().multiply(new BigDecimal(100)).longValue());

                opsRetailOrderVO.setRemark(odsExcelOrderDO.getRemarkInfo());
                opsRetailOrderVO.setChorgaId(OpsChannelEnum.getIdByName(odsExcelOrderDO.getOrderChannel()));
                opsRetailOrderVO.setBizCode("retail");
                opsRetailOrderVO.setShippingAddress(odsExcelOrderDO.getShippingAddress());
                opsRetailOrderVO.setConsigneeCity(odsExcelOrderDO.getConsigneeCity());
                opsRetailOrderVO.setConsigneeProvince(odsExcelOrderDO.getConsigneeProvince());
                opsRetailOrderVO.setConsigneeArea(odsExcelOrderDO.getConsigneeArea());
                opsRetailOrderVO.setPaymentTime(odsExcelOrderDO.getPayTime());
                opsRetailOrderVO.setSkuCode(odsExcelOrderDO.getSkuCode());
                opsRetailOrderVO.setAcceptorName(odsExcelOrderDO.getConsigneeName());
                opsRetailOrderVO.setAcceptorMobile(odsExcelOrderDO.getConsigneePhoneNo());
                opsRetailOrderVO.setChorgaId(OpsChannelEnum.getIdByName(odsExcelOrderDO.getOrderChannel()));
                opsRetailOrderVO.setShippingCost(odsExcelOrderDO.getShippingCost() == null ? null : odsExcelOrderDO.getShippingCost().multiply(new BigDecimal(100)).longValue());
                opsRetailOrderVO.setRefundAmount(odsExcelOrderDO.getRefundAmounts() == null ? null : odsExcelOrderDO.getRefundAmounts().multiply(new BigDecimal(100)).longValue());
                RefundParam refundParam = new RefundParam();

                RetailGoodsSnapshot goodsSnapshot = new RetailGoodsSnapshot();
                goodsSnapshot.setOrderAmount(odsExcelOrderDO.getOrderAmount() == null ? null : odsExcelOrderDO.getOrderAmount().multiply(new BigDecimal(100)).longValue());
                goodsSnapshot.setPurchaseQuantity(odsExcelOrderDO.getGoodsCount());

                Integer nameAndSourceName = OrderStatusEnum.getCodeByNameAndSourceName(odsExcelOrderDO.getOrderChannel(), odsExcelOrderDO.getRefundStatenName());
                if (nameAndSourceName != null){
                    Integer getmyCodeByCode = OrderStatusEnum.getmyCodeByCode(nameAndSourceName);
                    refundParam.setRefundState(OpsRefundStatusEnum.getOpsCodeBySelfCode(getmyCodeByCode));
                    if (OpsRefundStatusEnum.REFUND_SUC.getOpsCode().equals(OpsRefundStatusEnum.getOpsCodeBySelfCode(getmyCodeByCode))) {
                        refundParam.setRefundAmount(odsExcelOrderDO.getRefundAmounts() == null ? null : odsExcelOrderDO.getRefundAmounts().multiply(new BigDecimal(100)).longValue());
                        refundParam.setRefundTime(odsExcelOrderDO.getRefundTime());
                    }
                }

                if (OpsOrderStatusEnum.WAIT_PAY.getOpsCode().equals(OpsOrderStatusEnum.getOpsCodeBySelfCode(integer))){
                    opsRetailOrderVO.setPayAmount(0L);
                }else {
                    opsRetailOrderVO.setPayAmount(odsExcelOrderDO.getPayment() == null ? null : odsExcelOrderDO.getPayment().multiply(new BigDecimal(100)).longValue());
                }
                Integer opsCodeBySelfCode = OpsOrderStatusEnum.getOpsCodeBySelfCode(integer);
                if (OpsOrderStatusEnum.CLOSE.getOpsCode().equals(opsCodeBySelfCode) && (opsRetailOrderVO.getPayAmount() == null || opsRetailOrderVO.getPayAmount() == 0L)){
                    opsRetailOrderVO.setState(8);
                }
                goodsSnapshot.setSpecification(odsExcelOrderDO.getSpecification());
                opsRetailOrderVO.setRefundParam(refundParam);
                Long cost = opsRetailOrderVO.getShippingCost() == null ? 0L : opsRetailOrderVO.getShippingCost();
                Long disCount = opsRetailOrderVO.getDiscountAmount() == null ? 0L : opsRetailOrderVO.getDiscountAmount();
                Long payAmount = opsRetailOrderVO.getPayAmount() == null ? 0L : opsRetailOrderVO.getPayAmount();
                //订单总金额
                opsRetailOrderVO.setTotalAmount(cost + disCount + payAmount);
                goodsSnapshot.setSkuCode(odsExcelOrderDO.getSkuCode());
                goodsSnapshot.setGoodsId(odsExcelOrderDO.getGoodsId());
                goodsSnapshot.setGoodsName(odsExcelOrderDO.getGoodsName());
                OperatorSnapshot operatorSnapshot = new OperatorSnapshot();
                operatorSnapshot.setName(odsExcelOrderDO.getSingle());
                opsRetailOrderVO.setRetailGoodsSnapshot(goodsSnapshot);
                opsRetailOrderVO.setOperatorSnapshot(operatorSnapshot);
                opsRetailOrderVO.setSourceType(3);
                opsRetailOrderVO.setOrderType(1);
                opsRetailOrderVO.setChorgaType(2);
                opsRetailOrderVO.setGmtCreated(odsExcelOrderDO.getSingleTime());
                objects.add(opsRetailOrderVO);
            }
            reqParam.setRequestId(requestVO.getRequestId());
            reqParam.setRetailOrderUpdateParamList(objects);

        }

        return reqParam;
    }

    /**
     * 提取订单信息
     *
     * @param oldOrderMap  oldOrderMap
     * @param orderList    orderList
     * @param odsOrder     odsOrder
     * @param changeOrders changeOrders
     * @return void
     * @author zxw
     * @date 2022/4/13 2022/4/13
     */
    private void extractedOrder(HashMap<String, MdcOrderDO> oldOrderMap, List<MdcOrderDO> orderList, OdsExcelOrderDO odsOrder, List<MdcOrderDO> changeOrders, String sourceName) {

        MdcOrderDO mcrmOrderOld = oldOrderMap.get(odsOrder.getOrderCode() + "-" + odsOrder.getSkuCode());
        MdcOrderDO mcrmOrderDO = new MdcOrderDO();
        if (mcrmOrderOld != null) {
            mcrmOrderDO.setId(mcrmOrderOld.getId());
        }

        mcrmOrderDO.setChildOrder(odsOrder.getOrderCode() + "-" + odsOrder.getSkuCode());
        mcrmOrderDO.setOrderSplitId(odsOrder.getOrderCode());


        Integer nameAndSourceName = OrderStatusEnum.getCodeByNameAndSourceName(odsOrder.getOrderChannel(), odsOrder.getRefundStatenName());
        if (nameAndSourceName != null){
            Integer getmyCodeByCode = OrderStatusEnum.getmyCodeByCode(nameAndSourceName);
            mcrmOrderDO.setRefundStatus(OpsRefundStatusEnum.getOpsCodeBySelfCode(getmyCodeByCode));

        }else {
            mcrmOrderDO.setRefundStatus(0);
        }
        mcrmOrderDO.setSource(odsOrder.getOrderChannel());
        mcrmOrderDO.setGmtModified(new Date());
        mcrmOrderDO.setOrderStatus(OrderStatusEnum.getCodeByNameAndSourceName(sourceName, odsOrder.getOrderStatus()));

        mcrmOrderDO.setSelfOrderStatus(OrderStatusEnum.getmyCodeByCode(mcrmOrderDO.getOrderStatus()));

        mcrmOrderDO.setGoodsName(odsOrder.getGoodsName());
        mcrmOrderDO.setSkuName(odsOrder.getSkuName());
        BigDecimal orderAmount = odsOrder.getOrderAmount() == null ? BigDecimal.ZERO : odsOrder.getOrderAmount();
        mcrmOrderDO.setGoodsPrice(orderAmount.multiply(new BigDecimal(100)));
        mcrmOrderDO.setOrderAmount(odsOrder.getUserPayment() == null ? null : odsOrder.getUserPayment().multiply(new BigDecimal(100)));
        mcrmOrderDO.setContactPhoneNo(odsOrder.getContactPhoneNo());
        mcrmOrderDO.setUserName(odsOrder.getName());
        mcrmOrderDO.setCardId(odsOrder.getIdCard());
        mcrmOrderDO.setOrderCreateTime(odsOrder.getOrderCreateTime());
        mcrmOrderDO.setPayTime(odsOrder.getPayTime());
        mcrmOrderDO.setRefundReason(odsOrder.getRefundReason());
        mcrmOrderDO.setAgreementMerchant(odsOrder.getAgreementMerchant());
        mcrmOrderDO.setRefundTime(odsOrder.getRefundTime());
        mcrmOrderDO.setRefundAmount(odsOrder.getRefundAmount() == null ? null : odsOrder.getRefundAmount().multiply(new BigDecimal(100)));
        mcrmOrderDO.setSkuCode(odsOrder.getTpSkuCode());


        orderList.add(mcrmOrderDO);

        if (null == mcrmOrderOld || !mcrmOrderOld.equals(mcrmOrderDO) || (!mcrmOrderOld.getRefundStatus().equals(mcrmOrderDO.getRefundStatus()))) {
            changeOrders.add(mcrmOrderDO);
        }
    }

    /**
     * 提取客户信息
     *
     * @param oldClientMap        oldClientMap
     * @param clientList          clientList
     * @param mcrmClientOrderList mcrmClientOrderList
     * @param odsOrder            odsOrder
     * @return void
     * @author zxw
     * @date 2022/4/13 2022/4/13
     */
    private void extractedClient(HashMap<String, MdcClientDO> oldClientMap, HashSet<MdcClientDO> clientList, List<MdcClientOrderDO> mcrmClientOrderList, OdsExcelOrderDO odsOrder) {
        String phoneNo = odsOrder.getPhoneNo();
        if (StringUtils.isNotBlank(phoneNo)) {
            MdcClientDO mcrmClientOld = oldClientMap.get(phoneNo);
            MdcClientDO mcrmClientDO = new MdcClientDO();
            if (mcrmClientOld != null) {
                mcrmClientDO.setId(mcrmClientOld.getId());
            }
            mcrmClientDO.setSource(odsOrder.getOrderChannel());
            mcrmClientDO.setGmtModified(new Date());
            mcrmClientDO.setName(odsOrder.getName());
            mcrmClientDO.setPhoneNo(phoneNo);
            mcrmClientDO.setSex(2);
            mcrmClientDO.setIdCard(odsOrder.getIdCard());
            mcrmClientDO.setContactAddress("");

            clientList.add(mcrmClientDO);

            MdcClientOrderDO clientOrderDO = new MdcClientOrderDO();
            clientOrderDO.setOrderSplitId(odsOrder.getOrderCode());
            clientOrderDO.setPhoneNo(phoneNo);
            clientOrderDO.setChildOrder(odsOrder.getOrderCode() + odsOrder.getSkuCode());
            mcrmClientOrderList.add(clientOrderDO);
        }
    }

    /**
     * 提取预约信息
     *
     * @param oldOrderAppointMap oldOrderAppointMap
     * @param orderAppointList   orderAppointList
     * @param odsOrder           odsOrder
     * @return void
     * @author zxw
     * @date 2022/4/13 2022/4/13
     */
    private void extractedOrderAppoint(HashMap<String, MdcOrderAppointDO> oldOrderAppointMap, List<MdcOrderAppointDO> orderAppointList, OdsExcelOrderDO odsOrder) {

        String firstAppointTime = odsOrder.getFirstAppointTime();
        if (!StringUtils.isEmpty(firstAppointTime)) {

            MdcOrderAppointDO mcrmOrderAppointOld = oldOrderAppointMap.get(odsOrder.getOrderCode());
            MdcOrderAppointDO mcrmOrderAppointDO = new MdcOrderAppointDO();
            if (mcrmOrderAppointOld != null) {
                mcrmOrderAppointDO.setId(mcrmOrderAppointOld.getId());

            }
            mcrmOrderAppointDO.setOrderSplitId(odsOrder.getOrderCode());
            mcrmOrderAppointDO.setSource(odsOrder.getOrderChannel());
            mcrmOrderAppointDO.setGmtModified(new Date());

            mcrmOrderAppointDO.setFirstAppointTime(firstAppointTime);
            mcrmOrderAppointDO.setSecondAppointTime(odsOrder.getSecondAppointTime());
            mcrmOrderAppointDO.setThirdAppointTime(odsOrder.getThirdAppointTime());
            mcrmOrderAppointDO.setChildOrder(odsOrder.getChildOrder());
            orderAppointList.add(mcrmOrderAppointDO);

        }
    }


}
