package com.meerkat.vaccine.order.adapter.hander;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meerkat.notice.adapter.config.RobotNoticeConfig;
import com.meerkat.vaccine.order.config.DingxiangOrderConfig;
import com.meerkat.vaccine.order.domain.*;
import com.meerkat.vaccine.order.model.dto.DingXiang.*;
import com.meerkat.vaccine.order.model.dto.DingXiang.sku.DingXiangItemInfoDTO;
import com.meerkat.vaccine.order.model.dto.DingXiang.sku.DingXiangSkuListDTO;
import com.meerkat.vaccine.order.model.enums.OrderStatusEnum;
import com.meerkat.vaccine.order.model.enums.SourceSystemEnum;
import com.meerkat.vaccine.order.model.vo.OpsOrderVO;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;
import com.meerkat.vaccine.order.service.MdcGoodsService;
import com.meerkat.vaccine.order.service.OdsDingXiangClientService;
import com.meerkat.vaccine.order.service.OdsDingXiangOrderService;
import com.meerkat.vaccine.order.service.SysKvService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 丁香实现
 *
 * @author zhujx
 * @date 2022/03/14 15:03
 */
@Slf4j
@Service
public class DingXiangHandlerAdapter extends BaseApiSourceHandlerAdapter {

    @Autowired
    private SysKvService sysKvService;
    @Autowired
    private OdsDingXiangClientService odsDingXiangClientService;
    @Autowired
    private OdsDingXiangOrderService odsDingXiangOrderService;
    @Autowired
    private DingxiangOrderConfig dingxiangOrderConfig;
    @Autowired
    private MdcGoodsService mdcGoodsService;
    @Autowired
    private RobotNoticeConfig robotNoticeConfig;

    private static final String url = "https://open.feishu.cn/open-apis/bot/v2/hook/96aab723-458c-4604-b479-27900e47fb46";


    @Override
    public String getSource() {
        return SourceSystemEnum.DING_XIANG.getCode();
    }

    @Override
    public String getName() {
        return SourceSystemEnum.DING_XIANG.getName();
    }


    @Override
    public void obtainData(String sourceName) {
        Long dateYmdLong = getDateTime();

        List<SysKvDO> cookies = sysKvService.getValueLikeKey("DING_XIANG_ORG_COOKIES_");

        List<OdsDingXiangOrderDO> orders = new ArrayList<>();
        List<OdsDingXiangClientDO> clients = new ArrayList<>();

        for (SysKvDO kv : cookies) {

//            String city = DingXiangOrgEnum.getNameByCode(kv.getK());
            String city = kv.getT();

            String cookie = kv.getV();
            List<String> itemIdList = null;
            try {
                itemIdList = dingxiangOrderConfig.list(cookie);
            } catch (Exception e) {
                robotNoticeConfig.noticeOrderException("获取订单列表失败", url);
            }

            for (String itemId : itemIdList) {
                OdsDingXiangOrderDO order = new OdsDingXiangOrderDO();

                DingXiangOrderDTO dingXiangOrderDTO = null;
                try {
                    dingXiangOrderDTO = dingxiangOrderConfig.getOrderDetail(cookie, itemId);
                } catch (Exception e) {
                    robotNoticeConfig.noticeOrderException("获取订单详情失败", url);
                }

                if (dingXiangOrderDTO == null) {
                    continue;
                }

                //获取预约信息
                List<DingXiangRecordInfoDTO> recordList = dingXiangOrderDTO.getRecordList();
                List<String> reserveOrderIdList = recordList.stream().map(DingXiangRecordInfoDTO::getReserveOrderId).collect(Collectors.toList());

                OdsDingXiangClientDO odsDingXiangClientDO = new OdsDingXiangClientDO();

                List<DingXiangOperationRecordDTO> operationRecordList = new ArrayList<>();

                List<DingXiangReserveInfoDTO> reserveInfoList = new ArrayList<>();
                //获取预约客户详情
                for (String reserveOrderId : reserveOrderIdList) {
                    DingXiangReserveDTO dingXiangReserveDTO = null;
                    try {
                        dingXiangReserveDTO = dingxiangOrderConfig.getClientDetail(cookie, reserveOrderId);
                    } catch (Exception e) {
                        robotNoticeConfig.noticeOrderException("获取客户信息失败", url);
                    }
                    if (null == dingXiangReserveDTO) {
                        continue;
                    }
                    DingXiangReserveUserInfoDTO reserveUserInfo = dingXiangReserveDTO.getReserveUserInfo();
                    odsDingXiangClientDO = JSONObject.parseObject(JSONObject.toJSONString(reserveUserInfo), OdsDingXiangClientDO.class);
                    operationRecordList.addAll(dingXiangReserveDTO.getOperationRecordList());
                    reserveInfoList.add(dingXiangReserveDTO.getReserveInfo());

                }

                DingXiangOrderInfoDTO orderInfo1 = dingXiangOrderDTO.getOrderInfo();
                DingXiangUserSimpleInfoDTO userInfo = dingXiangOrderDTO.getUserInfo();
                DingXiangRefundInfoDTO refundInfo = dingXiangOrderDTO.getRefundInfo();

                order.setDataTimeInt(dateYmdLong);
                order.setOrderSplitId(orderInfo1.getOrderSplitId());
                order.setOrderItemId(itemId);
                order.setOrderStatus(orderInfo1.getOrderStatus());
                order.setPayStatus(orderInfo1.getPayStatus());
                order.setOrderCreateTime(orderInfo1.getOrderCreateTime());
                order.setServiceService("【" + city + "】" + orderInfo1.getServiceService());
                order.setSpecification("【" + city + "】" + orderInfo1.getSpecification());
                order.setAmount(orderInfo1.getAmount());
                order.setContactPhoneNo(userInfo.getContactPhoneNo());
                order.setOrderRemarks(userInfo.getOrderRemarks());
                order.setReserveInfo(JSONObject.toJSONString(reserveInfoList));
                order.setOperatingRecord(JSONObject.toJSONString(operationRecordList));
                order.setRefundInfo(JSONObject.toJSONString(refundInfo));

                if (odsDingXiangClientDO.getName() != null) {
                    odsDingXiangClientDO.setOrderSplitId(orderInfo1.getOrderSplitId());
                    odsDingXiangClientDO.setDataTimeInt(dateYmdLong);
                    clients.add(odsDingXiangClientDO);
                }
                orders.add(order);

            }
        }

        LambdaQueryWrapper<OdsDingXiangOrderDO> orderQuery = new LambdaQueryWrapper<>();
        orderQuery.eq(OdsDingXiangOrderDO::getDataTimeInt, dateYmdLong);
        odsDingXiangOrderService.remove(orderQuery);
        odsDingXiangOrderService.saveBatch(orders);

        LambdaQueryWrapper<OdsDingXiangClientDO> clientQuery = new LambdaQueryWrapper<>();
        clientQuery.eq(OdsDingXiangClientDO::getDataTimeInt, dateYmdLong);
        odsDingXiangClientService.remove(clientQuery);
        odsDingXiangClientService.saveBatch(clients);
    }

    @Override
    public OrderUpdateReqParam clearOrder(String sourceName) {
        Long dateYmdLong = getDateTime();

        //查询ods层订单信息
        LambdaQueryWrapper<OdsDingXiangOrderDO> odsOrderQuery = new LambdaQueryWrapper<>();
        odsOrderQuery.eq(OdsDingXiangOrderDO::getDataTimeInt, dateYmdLong);
        List<OdsDingXiangOrderDO> odsOrders = odsDingXiangOrderService.list(odsOrderQuery);

        Map<String, MdcGoodsDO> oldSkus = getOldSku();

        HashMap<String, MdcOrderDO> oldOrderMap = getOldOrderMap(getName());

        HashMap<String, MdcOrderAppointDO> oldOrderAppointMap = getOldOrderAppointMap(getName());

        //发生变更的订单
        List<MdcOrderDO> changeOrders = new ArrayList<>();

        List<MdcOrderAppointDO> appointList = new ArrayList<>();
        List<MdcOrderDO> orderList = new ArrayList<>();
        for (OdsDingXiangOrderDO oneOds : odsOrders) {
            String orderSplitId = oneOds.getOrderSplitId();

            MdcOrderDO mcrmOrderOld = oldOrderMap.get(orderSplitId);
            MdcOrderDO mcrmOrderDO = new MdcOrderDO();
            if (mcrmOrderOld != null) {
                mcrmOrderDO.setId(mcrmOrderOld.getId());
            }
            mcrmOrderDO.setIsNotice(false);
            mcrmOrderDO.setGmtModified(new Date());
            mcrmOrderDO.setSource(getName());
            mcrmOrderDO.setOrderSplitId(oneOds.getOrderSplitId());
            mcrmOrderDO.setOrderCreateTime(oneOds.getOrderCreateTime());
            mcrmOrderDO.setOrderStatus(oneOds.getOrderStatus());
            mcrmOrderDO.setSkuCode(oneOds.getSpecificationId());

            mcrmOrderDO.setSelfOrderStatus(OrderStatusEnum.getmyCodeByCode(mcrmOrderDO.getOrderStatus()));

            //获取退款详情
            DingXiangRefundInfoDTO dingXiangRefundInfoDTO = JSONObject.parseObject(oneOds.getRefundInfo(), DingXiangRefundInfoDTO.class);
            mcrmOrderDO.setRefundStatus(OrderStatusEnum.getSelfCodeByNameCodeAndCode(sourceName, dingXiangRefundInfoDTO.getRefundStatus()));
            mcrmOrderDO.setRefundTime(dingXiangRefundInfoDTO.getRefundTime());
            mcrmOrderDO.setGoodsName(oneOds.getServiceService());
            String specification = oneOds.getSpecification();
            MdcGoodsDO mdcGoodsDO = oldSkus.get(specification);
            if (mdcGoodsDO == null){
                log.warn(oneOds.getOrderSplitId() + "-" +specification);
            }
            mcrmOrderDO.setGoodsId(mdcGoodsDO.getGoodsId() == null ? null : mdcGoodsDO.getGoodsId());
            mcrmOrderDO.setSkuName(specification);
            mcrmOrderDO.setSkuCode(mdcGoodsDO.getSkuId() == null ? null : mdcGoodsDO.getSkuId());
            mcrmOrderDO.setOrderAmount(oneOds.getAmount());
            mcrmOrderDO.setContactPhoneNo(oneOds.getContactPhoneNo());
            mcrmOrderDO.setUserName("");
            mcrmOrderDO.setRefundAmount(dingXiangRefundInfoDTO.getRefundAmount());
            mcrmOrderDO.setRefundReason(dingXiangRefundInfoDTO.getReason());
            mcrmOrderDO.setPayTime(oneOds.getOrderCreateTime());
            //获取订单预约时间
            String reserveInfo = oneOds.getReserveInfo();
            List<DingXiangReserveInfoDTO> dingXiangReserveInfoList = JSONArray.parseArray(reserveInfo, DingXiangReserveInfoDTO.class);
            if (null != dingXiangReserveInfoList && !dingXiangReserveInfoList.isEmpty()) {

                MdcOrderAppointDO mcrmOrderAppointDO = oldOrderAppointMap.get(orderSplitId);

                if (mcrmOrderAppointDO == null) {
                    mcrmOrderAppointDO = new MdcOrderAppointDO();
                    mcrmOrderAppointDO.setSource(getName());
                    mcrmOrderAppointDO.setOrderSplitId(orderSplitId);
                }
                mcrmOrderAppointDO.setGmtModified(new Date());
                DingXiangReserveInfoDTO dingXiangReserveInfoDTO = dingXiangReserveInfoList.get(0);
                mcrmOrderAppointDO.setFirstAppointTime(dingXiangReserveInfoDTO.getReserveDateTime().getReserveDate());

                appointList.add(mcrmOrderAppointDO);

                if (!StringUtils.isBlank(dingXiangReserveInfoDTO.getReserveDateTime().getReserveDate())){
                    mcrmOrderDO.setOperateRemark("预约时间："+dingXiangReserveInfoDTO.getReserveDateTime().getReserveDate());
                }
            }
            String operatingRecord = oneOds.getOperatingRecord();
            List<DingXiangOperationRecordDTO> operationRecordList = JSONArray.parseArray(operatingRecord, DingXiangOperationRecordDTO.class);
            if (null != operationRecordList && !operationRecordList.isEmpty()) {
                for (DingXiangOperationRecordDTO oneOperation : operationRecordList){
                    String type = oneOperation.getType();
                    if (!StringUtils.isBlank(type) && type.equals("核销预约")){
                        mcrmOrderDO.setSecondOrderCheckTime(oneOperation.getDate());
                    }
                }
            }

            if (null == mcrmOrderOld || !mcrmOrderOld.equals(mcrmOrderDO)) {
                mcrmOrderDO.setIsNotice(true);
                changeOrders.add(mcrmOrderDO);
            }

            orderList.add(mcrmOrderDO);

        }
        mdcOrderAppointService.saveOrUpdateBatch(appointList);

        List<OpsOrderVO> opsOrderList = mdcOrderToOpsService.orderToOps(changeOrders);

        OrderUpdateReqParam requestVO = putLocalMemory(orderList);
        log.info("{}-requestId:{}", sourceName, requestVO.getRequestId());

        orderToOps(opsOrderList, requestVO.getRequestId());

        requestVO.setOrderUpdateParamList(opsOrderList);

        return requestVO;
    }

    @Override
    public void clearClient() {
        Long dateYmdLong = getDateTime();

        //查询ods层客户数据
        LambdaQueryWrapper<OdsDingXiangClientDO> queryOds = new LambdaQueryWrapper<>();
        queryOds.eq(OdsDingXiangClientDO::getDataTimeInt, dateYmdLong);
        List<OdsDingXiangClientDO> list = odsDingXiangClientService.list(queryOds);

        HashMap<String, MdcClientDO> oldClientMap = getOldClientMap(getName());

        HashSet<MdcClientDO> saveClients = new HashSet<>();
        List<MdcClientOrderDO> saveClientOrder = new ArrayList<>();
        List<String> ordersId = new ArrayList<>();
        for (OdsDingXiangClientDO oneOdsClient : list) {
            String contactPhoneNo = oneOdsClient.getContactPhoneNo();
            ordersId.add(oneOdsClient.getOrderSplitId());

            MdcClientDO mcrmClientDO = oldClientMap.get(contactPhoneNo);

            if (mcrmClientDO == null) {
                mcrmClientDO = new MdcClientDO();
                mcrmClientDO.setSource(getName());
            }
            mcrmClientDO.setGmtModified(new Date());
            mcrmClientDO.setName(oneOdsClient.getName());
            mcrmClientDO.setPhoneNo(contactPhoneNo);
            mcrmClientDO.setSex(oneOdsClient.getGender());
            mcrmClientDO.setIdCard(oneOdsClient.getIdCard());
            mcrmClientDO.setContactAddress(oneOdsClient.getContactAddress());
            saveClients.add(mcrmClientDO);

            MdcClientOrderDO clientOrderDO = new MdcClientOrderDO();
            clientOrderDO.setOrderSplitId(oneOdsClient.getOrderSplitId());
            clientOrderDO.setPhoneNo(contactPhoneNo);
            saveClientOrder.add(clientOrderDO);


        }
        mdcClientService.saveOrUpdateBatch(saveClients);

        //删除中间表关联关系
        if (!ordersId.isEmpty()) {
            LambdaQueryWrapper<MdcClientOrderDO> query = new LambdaQueryWrapper<>();
            query.in(MdcClientOrderDO::getOrderSplitId, ordersId);
            mdcClientOrderService.remove(query);
        }

        mdcClientOrderService.saveBatch(saveClientOrder);
    }


    @Override
    public void getSku() {
        List<MdcGoodsDO> skus = new ArrayList<>();
        Map<String, Long> oldSkus = getOldSkuId();

        List<SysKvDO> cookies = sysKvService.getValueLikeKey("DING_XIANG_ORG_COOKIES_");
        for (SysKvDO kv : cookies) {

//            String city = DingXiangOrgEnum.getNameByCode(kv.getK());
            String city = kv.getT();

            String cookie = kv.getV();
            List<DingXiangItemInfoDTO> itemInfoDTOS = null;
            try {
                itemInfoDTOS = dingxiangOrderConfig.skuList(cookie);
            } catch (Exception e) {
                robotNoticeConfig.noticeOrderException("获取sku失败", url);
            }

            for (DingXiangItemInfoDTO itemInfoDTO : itemInfoDTOS) {
                String serviceName = itemInfoDTO.getServiceName();
                if (!StringUtils.isBlank(serviceName) && serviceName.contains("停用")) {
                    continue;
                }
                String commodityId = itemInfoDTO.getCommodityId();
                List<DingXiangSkuListDTO> skuList = itemInfoDTO.getSkuList();
                for (DingXiangSkuListDTO sku : skuList) {
                    MdcGoodsDO skuDO = new MdcGoodsDO();
                    skuDO.setSource("丁香园");
                    skuDO.setGoodsId(commodityId);
                    skuDO.setGoodsName(serviceName);

                    String skuId = sku.getSkuId();
                    String specificationOptionNames = sku.getSpecificationOptionNames();

                    skuDO.setSkuId(skuId);
                    skuDO.setSkuName("【" + city + "】" + specificationOptionNames);
                    Long id = oldSkus.get(skuId);
                    if (null != id) {
                        skuDO.setId(id);
                        skuDO.setGmtModified(new Date());
                    }

                    skus.add(skuDO);
                }
            }
        }
        mdcGoodsService.saveOrUpdateBatch(skus);

    }

    public Map<String, Long> getOldSkuId() {
        LambdaQueryWrapper<MdcGoodsDO> query = new LambdaQueryWrapper<>();
        query.eq(MdcGoodsDO::getDeleted, 0);
        List<MdcGoodsDO> list = mdcGoodsService.list(query);
        Map<String, Long> collect = list.stream().collect(Collectors.toMap(MdcGoodsDO::getSkuId, MdcGoodsDO::getId));
        return collect;
    }

    public Map<String, MdcGoodsDO> getOldSku() {
        HashMap<String, MdcGoodsDO> oldClientMap = new HashMap<>();
        LambdaQueryWrapper<MdcGoodsDO> query = new LambdaQueryWrapper<>();
        query.eq(MdcGoodsDO::getDeleted, 0);
        List<MdcGoodsDO> list = mdcGoodsService.list(query);
        list.forEach(s -> oldClientMap.put(s.getSkuName(), s));
        return oldClientMap;
    }

}
