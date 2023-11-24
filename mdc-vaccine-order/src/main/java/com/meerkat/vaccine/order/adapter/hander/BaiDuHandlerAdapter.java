package com.meerkat.vaccine.order.adapter.hander;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meerkat.common.model.enums.SexEnum;
import com.meerkat.vaccine.order.config.BaiDuOrderConfig;
import com.meerkat.vaccine.order.domain.*;
import com.meerkat.vaccine.order.model.dto.baidu.*;
import com.meerkat.vaccine.order.model.enums.BaiDuSkuCode;
import com.meerkat.vaccine.order.model.enums.OrderStatusEnum;
import com.meerkat.vaccine.order.model.enums.SourceSystemEnum;
import com.meerkat.vaccine.order.model.vo.OpsOrderVO;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;
import com.meerkat.vaccine.order.service.OdsBaiDuClientService;
import com.meerkat.vaccine.order.service.OdsBaiDuOrderService;
import com.meerkat.vaccine.order.util.NameCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 丁香实现
 *
 * @author zhujx
 * @date 2022/03/14 15:03
 */
@Slf4j
@Service
public class BaiDuHandlerAdapter extends BaseApiSourceHandlerAdapter {

    @Autowired
    private BaiDuOrderConfig baiDuOrderConfig;

    @Autowired
    private OdsBaiDuClientService odsBaiDuClientService;

    @Autowired
    private OdsBaiDuOrderService odsBaiDuOrderService;

    @Override
    public String getSource() {
        return SourceSystemEnum.BAI_DU.getCode();
    }

    @Override
    public String getName() {
        return SourceSystemEnum.BAI_DU.getName();
    }


    @Override
    public void obtainData(String sourceName) {
        Long dateYmdLong = getDateTime();

        Set<String> orderIds = baiDuOrderConfig.toGetOrderList();


        List<OdsBaiDuOrderDO> odsBaiDuOrderList = new ArrayList<>();
        List<OdsBaiDuClientDO> odsBaiDuClientList = new ArrayList<>();

        for (String orderId : orderIds) {

            OrderDetailDTO orderDetailDTO = baiDuOrderConfig.toGetOrderDetail(orderId);

            OrderDataDTO data = orderDetailDTO.getData();

            GoodsDTO goodsDTO = new GoodsDTO();
            try {
                goodsDTO = data.getGoods().get(0);
            } catch (Exception e) {
                log.error("error-orderId:" + orderId);
            }

            Long skuId = goodsDTO.getSkuId();

            Long newStoreId = data.getNewStoreId();

            //获取sku信息
//            SkuItemDTO skuItemDTO = baiDuOrderConfig.toGetOrderSku(skuId, newStoreId);
//            GoodsSkuDetailDTO skuDetails = skuItemDTO.getData();
//            String goodsName = goodsDTO.getTitleName();
            long backMoneyFen = data.getBackMoneyFen() == null ? 0L : data.getBackMoneyFen();
            long goodsPriceFen = data.getGoodsPriceFen() == null ? 0L : data.getGoodsPriceFen();
            long originalPriceFen = data.getOriginalPriceFen() == null ? 0L : data.getOriginalPriceFen();
            long paidAmountFen = data.getPaidAmountFen() == null ? 0L : data.getPaidAmountFen();
            long bdDiscount = data.getBdDiscount() == null ? 0L : data.getBdDiscount();
            long userPayment = data.getUserPayment() == null ? 0L : data.getUserPayment();

            OdsBaiDuOrderDO odsOrder = new OdsBaiDuOrderDO();
            odsOrder.setDataTimeInt(dateYmdLong);
            odsOrder.setGmtModified(new Date());
            odsOrder.setOrderId(data.getOrderId());
            odsOrder.setStatus(data.getStatus());
            odsOrder.setAgreeRefundTime(data.getAgreeRefundTime());
            odsOrder.setApplyRefundReason(data.getApplyRefundReason());
            odsOrder.setApplyRefundTime(data.getApplyRefundTime());
            odsOrder.setAppointDate(data.getAppointDate());
            odsOrder.setAppointMobile(data.getAppointMobile());
            odsOrder.setAppointUserName(data.getAppointUserName());
            odsOrder.setAssignName(data.getAssignName());
            odsOrder.setBackMoneyFen(new BigDecimal(backMoneyFen));
            odsOrder.setCreateTime(data.getCreateTime());
            odsOrder.setGoodsName(goodsDTO.getTitleName());
            odsOrder.setGoodsId(goodsDTO.getGoodsId());
            odsOrder.setSkuName(goodsDTO.getTitleName());
            odsOrder.setTpSkuCode(goodsDTO.getSkuId()+"");
            odsOrder.setGoodsPriceFen(new BigDecimal(goodsPriceFen));
            odsOrder.setMessage(data.getMessage());
            odsOrder.setOriginalPriceFen(new BigDecimal(originalPriceFen));
            odsOrder.setPaidAmountFen(new BigDecimal(paidAmountFen));
            odsOrder.setBdDiscount(new BigDecimal(bdDiscount));
            odsOrder.setUserPayment(new BigDecimal(userPayment));
            odsOrder.setPayTime(data.getPayTime());
            odsOrder.setPayType(data.getPayType());
            odsOrder.setSex(data.getSex());
            odsOrder.setReceMobile(data.getReceMobile());
            odsOrder.setReceName(data.getReceName());

            odsBaiDuOrderList.add(odsOrder);


            //预约人信息
            ClientDTO vaccinatedPerson = data.getVaccinatedPerson();
            if (null != vaccinatedPerson) {
                OdsBaiDuClientDO odsClient = new OdsBaiDuClientDO();
                odsClient.setDataTimeInt(dateYmdLong);
                odsClient.setGmtModified(new Date());
                odsClient.setOrderId(orderId);
                odsClient.setName(vaccinatedPerson.getName());
                odsClient.setGender(vaccinatedPerson.getSex());
                odsClient.setBirthday(vaccinatedPerson.getBirthday());
                odsClient.setIdCard(vaccinatedPerson.getIdCard());
                odsClient.setPhone(vaccinatedPerson.getPhone());
                odsClient.setCardType(vaccinatedPerson.getCardType());

                odsBaiDuClientList.add(odsClient);
            }

        }

        LambdaQueryWrapper<OdsBaiDuOrderDO> orderQuery = new LambdaQueryWrapper<>();
        orderQuery.eq(OdsBaiDuOrderDO::getDataTimeInt, dateYmdLong);
        odsBaiDuOrderService.remove(orderQuery);
        odsBaiDuOrderService.saveBatch(odsBaiDuOrderList);

        LambdaQueryWrapper<OdsBaiDuClientDO> clientQuery = new LambdaQueryWrapper<>();
        clientQuery.eq(OdsBaiDuClientDO::getDataTimeInt, dateYmdLong);
        odsBaiDuClientService.remove(clientQuery);
        odsBaiDuClientService.saveBatch(odsBaiDuClientList);
    }

    @Override
    public OrderUpdateReqParam clearOrder(String sourceName) {
        Long dateYmdLong = getDateTime();

        LambdaQueryWrapper<OdsBaiDuOrderDO> orderQuery = new LambdaQueryWrapper<>();
        orderQuery.eq(OdsBaiDuOrderDO::getDataTimeInt, dateYmdLong);
        List<OdsBaiDuOrderDO> orderDOList = odsBaiDuOrderService.list(orderQuery);

        HashMap<String, MdcOrderDO> oldOrderMap = getOldOrderMap(getName());

        HashMap<String, MdcOrderAppointDO> oldOrderAppointMap = getOldOrderAppointMap(getName());

        List<MdcOrderDO> changeOrders = new ArrayList<>();

        List<MdcOrderAppointDO> appointList = new ArrayList<>();

        List<MdcOrderDO> orderList = new ArrayList<>();

        for (OdsBaiDuOrderDO oneOds : orderDOList) {
            String orderSplitId = oneOds.getOrderId();

            MdcOrderDO mcrmOrderOld = oldOrderMap.get(orderSplitId);
            MdcOrderDO mcrmOrderDO = new MdcOrderDO();
            if (mcrmOrderOld != null) {
                mcrmOrderDO.setId(mcrmOrderOld.getId());

            }
            mcrmOrderDO.setGmtModified(new Date());
            mcrmOrderDO.setSource(SourceSystemEnum.BAI_DU.getName());
            mcrmOrderDO.setOrderSplitId(oneOds.getOrderId());
            mcrmOrderDO.setRefundStatus(null);
            mcrmOrderDO.setOrderCreateTime(oneOds.getCreateTime());
            mcrmOrderDO.setOrderStatus(oneOds.getStatus());

            mcrmOrderDO.setSelfOrderStatus(OrderStatusEnum.getSelfCodeByNameCodeAndCode(sourceName, mcrmOrderDO.getOrderStatus()));

            mcrmOrderDO.setGoodsName(oneOds.getGoodsName());
            mcrmOrderDO.setSkuName(oneOds.getSkuName());
            mcrmOrderDO.setGoodsPrice(oneOds.getOriginalPriceFen());
            mcrmOrderDO.setOrderAmount(oneOds.getUserPayment());
            mcrmOrderDO.setContactPhoneNo(oneOds.getAppointMobile());
            mcrmOrderDO.setUserName(oneOds.getReceName());
            mcrmOrderDO.setPayTime(oneOds.getPayTime());
            mcrmOrderDO.setRefundAmount(oneOds.getBackMoneyFen().compareTo(BigDecimal.ZERO) == 0 ? null : oneOds.getBackMoneyFen());
            mcrmOrderDO.setRefundTime(oneOds.getAgreeRefundTime());
            mcrmOrderDO.setGoodsId(oneOds.getGoodsId());
//            String skuCode = BaiDuSkuCode.getCodeById(orderSplitId);
            mcrmOrderDO.setSkuCode(oneOds.getTpSkuCode());

            orderList.add(mcrmOrderDO);


            String appointDate = oneOds.getAppointDate();

            if (!StringUtils.isEmpty(appointDate)) {

                MdcOrderAppointDO mcrmOrderAppointDO = oldOrderAppointMap.get(orderSplitId);

                if (mcrmOrderAppointDO == null) {
                    mcrmOrderAppointDO = new MdcOrderAppointDO();
                    mcrmOrderAppointDO.setSource(getName());
                    mcrmOrderAppointDO.setOrderSplitId(orderSplitId);
                }
                mcrmOrderAppointDO.setGmtModified(new Date());
                mcrmOrderAppointDO.setFirstAppointTime(appointDate);


                appointList.add(mcrmOrderAppointDO);
            }

            if (null == mcrmOrderOld || !mcrmOrderOld.equals(mcrmOrderDO)) {
                changeOrders.add(mcrmOrderDO);
            }

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

        LambdaQueryWrapper<OdsBaiDuClientDO> queryOds = new LambdaQueryWrapper<>();
        queryOds.eq(OdsBaiDuClientDO::getDataTimeInt, dateYmdLong);
        List<OdsBaiDuClientDO> list = odsBaiDuClientService.list(queryOds);


        HashMap<String, MdcClientDO> oldClientMap = getOldClientMap(getName());

        HashSet<MdcClientDO> saveClients = new HashSet<>();
        List<MdcClientOrderDO> saveClientOrder = new ArrayList<>();
        List<String> ordersId = new ArrayList<>();
        for (OdsBaiDuClientDO oneOdsClient : list) {

            String name = oneOdsClient.getName();
            boolean check = NameCheckUtil.checkName(name);
            if (check) {
                String contactPhoneNo = oneOdsClient.getPhone();
                ordersId.add(oneOdsClient.getOrderId());


                MdcClientDO mcrmClientDO = oldClientMap.get(contactPhoneNo);

                if (mcrmClientDO == null) {
                    mcrmClientDO = new MdcClientDO();
                    mcrmClientDO.setSource(getName());
                }
                mcrmClientDO.setGmtModified(new Date());
                mcrmClientDO.setName(name);
                mcrmClientDO.setPhoneNo(contactPhoneNo);
                mcrmClientDO.setSex(SexEnum.getCodeByName(oneOdsClient.getGender()));
                mcrmClientDO.setIdCard(oneOdsClient.getIdCard());
                mcrmClientDO.setContactAddress("");
                saveClients.add(mcrmClientDO);

                MdcClientOrderDO clientOrderDO = new MdcClientOrderDO();
                clientOrderDO.setOrderSplitId(oneOdsClient.getOrderId());
                clientOrderDO.setPhoneNo(contactPhoneNo);
                saveClientOrder.add(clientOrderDO);
            }

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
    void getSku() {

    }

}
