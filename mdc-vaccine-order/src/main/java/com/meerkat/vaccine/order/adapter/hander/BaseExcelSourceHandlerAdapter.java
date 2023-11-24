package com.meerkat.vaccine.order.adapter.hander;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meerkat.common.exception.BizException;
import com.meerkat.vaccine.order.domain.*;
import com.meerkat.vaccine.order.handel.FileHandler;
import com.meerkat.vaccine.order.model.enums.OpsChannelEnum;
import com.meerkat.vaccine.order.model.enums.OrderStatusEnum;
import com.meerkat.vaccine.order.model.vo.OpsOrderVO;
import com.meerkat.vaccine.order.model.vo.OrderExt;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;
import com.meerkat.vaccine.order.service.OdsExcelOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 渠道表格导入适配器
 *
 * @author zhujx
 * @date 2022/03/03 13:40
 */
@Slf4j
@Service
public class BaseExcelSourceHandlerAdapter extends BaseSourceHandlerAdapter {

    @Autowired
    private OdsExcelOrderService odsExcelOrderService;

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
    public void obtainData(String sourceName) {
        Long dateYmdLong = getDateTime();

        List<OdsExcelOrderDO> list = getImportOrderDataList(dateYmdLong, sourceName);

        LambdaQueryWrapper<OdsExcelOrderDO> queryOrder = new LambdaQueryWrapper<>();
        if (!sourceName.equals(YI_LU)){
            queryOrder.eq(OdsExcelOrderDO::getDataTimeInt, dateYmdLong).eq(OdsExcelOrderDO::getOrderChannel, sourceName);
        }else {
            queryOrder.eq(OdsExcelOrderDO::getDataTimeInt, dateYmdLong).in(OdsExcelOrderDO::getOrderChannel, YiLuList);
        }
        odsExcelOrderService.remove(queryOrder);
        odsExcelOrderService.saveBatch(list);
    }

    @Override
    public OrderUpdateReqParam clearOrder(String sourceName) {
        Long dateYmdLong = getDateTime();
        LambdaQueryWrapper<OdsExcelOrderDO> queryOrder = new LambdaQueryWrapper<>();
        if (!sourceName.equals(YI_LU)){
            queryOrder.eq(OdsExcelOrderDO::getDataTimeInt, dateYmdLong).eq(OdsExcelOrderDO::getOrderChannel, sourceName);
        }else {
            queryOrder.eq(OdsExcelOrderDO::getDataTimeInt, dateYmdLong).in(OdsExcelOrderDO::getOrderChannel, YiLuList);
        }

        List<OdsExcelOrderDO> listOds = odsExcelOrderService.list(queryOrder);
        return toMcrmOrderAndClient(listOds, sourceName);
    }

    @Override
    public void clearClient() {

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
    private List<OdsExcelOrderDO> getImportOrderDataList(Long dateYmdLong, String sourceName) {

        MultipartFile file = FileHandler.get();

        if (file == null) {
            throw new BizException("-1000", "文件不存在");
        }

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(0);
        params.setSheetNum(1);

        List<OdsExcelOrderDO> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), OdsExcelOrderDO.class, params);
            if (sourceName.equals(YI_LU)){
                list = list.stream().filter(s -> StringUtils.isNotBlank(s.getOrderCode()) && YiLuList.contains(s.getOrderChannel())).peek(s -> s.setDataTimeInt(dateYmdLong)).collect(Collectors.toList());
            }else {
                list = list.stream().filter(s -> StringUtils.isNotBlank(s.getOrderCode()) && sourceName.equals(s.getOrderChannel())).peek(s -> s.setDataTimeInt(dateYmdLong)).collect(Collectors.toList());
            }

            //额外维护
            dealOrderTime(list,file,sourceName);

        } catch (Exception e) {
            throw new BizException("-1000", e.getMessage());
        }
        if (list.isEmpty()) {
            throw new BizException("-1000", "上传失败，请查看订单渠道映射是否正确");
        }
        return list;
    }

    /**
     * 时间处理（有些解析不到时间数据，额外处理）
     */
    private void dealOrderTime(List<OdsExcelOrderDO> list,MultipartFile file, String sourceName){
        try {
            if(CollectionUtils.isEmpty(list)){
                return;
            }
            Date orderCreateTime = list.get(0).getOrderCreateTime();
            //取到时间则退出
            if(orderCreateTime != null){
                return;
            }
            log.info("渠道：{}，出现获取不到时间数据",sourceName);
            Map<String,OdsExcelOrderDO> orderDOHashMap = new HashMap<>();
            List<Map<String, Object>> dataList = ExcelUtil.getReader(file.getInputStream()).readAll();
            for (Map<String, Object> objMap : dataList) {
                Object orderTime = objMap.get("订单创建时间");
                Object payTime = objMap.get("付款时间");
                Object orderNum = objMap.get("订单编号");
                Object refundTime = objMap.get("退款时间");

                OdsExcelOrderDO orderDO = new OdsExcelOrderDO();
                if(!ObjectUtil.isEmpty(orderTime)){
                    orderDO.setOrderCreateTime(DateUtil.parse(String.valueOf(orderTime),"yyyy-MM-dd HH:mm:ss"));
                }
                if(!ObjectUtil.isEmpty(payTime)){
                    orderDO.setPayTime(DateUtil.parse(String.valueOf(payTime),"yyyy-MM-dd HH:mm:ss"));
                }
                if(!ObjectUtil.isEmpty(refundTime)){
                    orderDO.setRefundTime(DateUtil.parse(String.valueOf(refundTime),"yyyy-MM-dd HH:mm:ss"));
                }
                orderDOHashMap.put(String.valueOf(orderNum),orderDO);
            }
            //数据维护
            OdsExcelOrderDO temp = null;
            for(OdsExcelOrderDO odsExcelOrderDO:list){
                if(!orderDOHashMap.containsKey(odsExcelOrderDO.getOrderCode())){
                    continue;
                }
                temp = orderDOHashMap.get(odsExcelOrderDO.getOrderCode());
                if(odsExcelOrderDO.getOrderCreateTime() == null){
                    odsExcelOrderDO.setOrderCreateTime(temp.getOrderCreateTime());
                }
                if(odsExcelOrderDO.getPayTime() == null){
                    odsExcelOrderDO.setPayTime(temp.getPayTime());
                }
                if(odsExcelOrderDO.getRefundTime() == null){
                    odsExcelOrderDO.setRefundTime(temp.getRefundTime());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        List<MdcOrderDO> orderList = new ArrayList<>();
        //发生变更的订单
        List<MdcOrderDO> changeOrders = new ArrayList<>();
        for (OdsExcelOrderDO odsOrder : list) {
            //提取订单信息
            extractedOrder(oldOrderMap, orderList, odsOrder, changeOrders, sourceName);
        }
        OrderUpdateReqParam requestVO = putLocalMemory(orderList);

        log.info("{}-requestId:{}", sourceName, requestVO.getRequestId());
        List<OpsOrderVO> opsOrderList = mdcOrderToOpsService.orderToOps(changeOrders);
        requestVO.setOrderUpdateParamList(opsOrderList);
        return requestVO;
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

        MdcOrderDO mcrmOrderOld = oldOrderMap.get(odsOrder.getOrderCode());
        MdcOrderDO mcrmOrderDO = new MdcOrderDO();
        if (mcrmOrderOld != null) {
            mcrmOrderDO.setId(mcrmOrderOld.getId());
        }
        mcrmOrderDO.setOrderSplitId(odsOrder.getOrderCode());
        mcrmOrderDO.setRefundStatus(null);
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
        mcrmOrderDO.setNum(odsOrder.getNum());
        try {
            mcrmOrderDO.setPayTime(odsOrder.getPayTime().getTime() == 0L ? null : odsOrder.getPayTime());
        } catch (NullPointerException e) {
            mcrmOrderDO.setPayTime(null);
        }
        mcrmOrderDO.setRefundReason(odsOrder.getRefundReason());
        mcrmOrderDO.setAgreementMerchant(odsOrder.getAgreementMerchant());
        mcrmOrderDO.setRefundTime(odsOrder.getRefundTime());
        mcrmOrderDO.setRefundAmount(odsOrder.getRefundAmount() == null ? null : odsOrder.getRefundAmount().multiply(new BigDecimal(100)));
        mcrmOrderDO.setSkuCode(odsOrder.getTpSkuCode());
        mcrmOrderDO.setOrderTags(odsOrder.getOrderTags());
        mcrmOrderDO.setBalance(odsOrder.getBalance() == null ? null : odsOrder.getBalance().multiply(new BigDecimal(100)));
        mcrmOrderDO.setIsPresale(odsOrder.getIsPresale());
        mcrmOrderDO.setDeposit(odsOrder.getDeposit() == null ? null : odsOrder.getDeposit().multiply(new BigDecimal(100)));
        mcrmOrderDO.setDepositPayTime(odsOrder.getDepositPayTime());
        mcrmOrderDO.setExamDate(odsOrder.getFirstAppointTime() == null ? null : DateUtil.parse(odsOrder.getFirstAppointTime(),"yyyy-MM-dd"));
        mcrmOrderDO.setFirstAppointTime(odsOrder.getFirstAppointTime() == null ? null : odsOrder.getFirstAppointTime());
        mcrmOrderDO.setOperateRemark(odsOrder.getRemark());
        mcrmOrderDO.setShopId(odsOrder.getShopId());
        mcrmOrderDO.setShopName(odsOrder.getShopName());
        mcrmOrderDO.setRefundAuditTime(odsOrder.getRefundAuditTime());
        buildTimestamp(odsOrder, mcrmOrderDO);
        orderList.add(mcrmOrderDO);
        if (null == mcrmOrderOld || !mcrmOrderOld.equals(mcrmOrderDO)) {
            changeOrders.add(mcrmOrderDO);
        }
    }

    private void buildTimestamp(OdsExcelOrderDO odsOrder, MdcOrderDO mcrmOrderDO) {
        Long chorgaId = null;

        if (!YiLuList.contains(odsOrder.getOrderChannel())){
            chorgaId = OpsChannelEnum.getIdByName(odsOrder.getOrderChannel());
        }else {
            chorgaId = OpsChannelEnum.getIdByName(YI_LU);
        }
        mcrmOrderDO.setExamWithinDays(7);

        if (chorgaId == 3 || chorgaId == 8){
             mcrmOrderDO.setExamWithinDays(90);
            if (mcrmOrderDO.getSkuName() != null && mcrmOrderDO.getSkuName().contains("24小时可约")){
                mcrmOrderDO.setExamWithinDays(1);
            }
            if (mcrmOrderDO.getSkuName() != null && mcrmOrderDO.getSkuName().contains("7天内可约")){
                mcrmOrderDO.setExamWithinDays(7);
            }
            if (mcrmOrderDO.getSkuName() != null && mcrmOrderDO.getSkuName().contains("15天内可约")){
                mcrmOrderDO.setExamWithinDays(15);
            }
            if (mcrmOrderDO.getSkuName() != null && (mcrmOrderDO.getSkuName().contains("30天内可约") || mcrmOrderDO.getSkuName().contains("预计30天内可约"))){
                mcrmOrderDO.setExamWithinDays(30);
            }
            if (mcrmOrderDO.getSkuName() != null && (mcrmOrderDO.getSkuName().contains("60天内可约") || mcrmOrderDO.getSkuName().contains("预计60天内可约"))){
                mcrmOrderDO.setExamWithinDays(60);
            }
            if (mcrmOrderDO.getSkuName() != null && (mcrmOrderDO.getSkuName().contains("90天内可约") || mcrmOrderDO.getSkuName().contains("预计90天内可约"))){
                mcrmOrderDO.setExamWithinDays(90);
            }
        }

        if (chorgaId== 7){
            if (mcrmOrderDO.getSkuName() != null && mcrmOrderDO.getSkuName().contains("天约不上必赔")){
                mcrmOrderDO.setExamWithinDays(90);
            }
        }

        try {
            if (chorgaId == 14){
                if (mcrmOrderDO.getSkuName() != null && mcrmOrderDO.getSkuName().contains("天未履约必赔")){
                    String skuName = mcrmOrderDO.getSkuName();
                    String[] test1 = skuName.split("天未履约必赔");
                    List<String> stringList = Arrays.asList(test1);
                    for (String s : stringList) {
                        mcrmOrderDO.setExamWithinDays(Integer.valueOf(s));
                    }
                }
            }
        } catch (NumberFormatException e) {
            log.info("{}-解析可约时间异常:{}", mcrmOrderDO.getSource(), mcrmOrderDO.getOrderSplitId());
        }

        if (chorgaId == 2){
            if (mcrmOrderDO.getSkuName() != null && mcrmOrderDO.getSkuName().contains("周末可约")){
                mcrmOrderDO.setExamWithinDays(7);
            }
        }

        if (chorgaId == 5){
            if (mcrmOrderDO.getSkuName() != null && (mcrmOrderDO.getSkuName().contains("天可约") || mcrmOrderDO.getSkuName().contains("天开针"))){
                mcrmOrderDO.setExamWithinDays(90);
            }
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
            orderAppointList.add(mcrmOrderAppointDO);

        }
    }
}
