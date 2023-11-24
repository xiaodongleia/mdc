package com.meerkat.vaccine.order.config;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.common.utils.CommHttpUtils;
import com.meerkat.vaccine.order.model.dto.DingXiang.DingXiangOrderDTO;
import com.meerkat.vaccine.order.model.dto.DingXiang.DingXiangOrderItemDTO;
import com.meerkat.vaccine.order.model.dto.DingXiang.DingXiangReserveDTO;
import com.meerkat.vaccine.order.model.dto.DingXiang.DingXiangReserveItemDTO;
import com.meerkat.vaccine.order.model.dto.DingXiang.sku.DingXiangItemInfoDTO;
import com.meerkat.vaccine.order.model.dto.DingXiang.sku.DingXiangServiceInfoDTO;
import com.meerkat.vaccine.order.model.dto.ItemDTO;
import com.meerkat.vaccine.order.model.dto.OrderDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2022/03/02 15:23
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "order.config.dingxiang")
public class DingxiangOrderConfig {

    //获取订单列表
    private String listUrl;

    //获取订单详情
    private String detailUrl;

    //获取客户详情
    private String clientUrl;

    //获取sku链接
    private String skuUrl;


    public List<String> list(String cookie) {
        List<ItemDTO> list = new ArrayList<>();
        getOrderList(list, 1, cookie);
        //获取itemid，通过itemid查询订单详情
        return list.stream().map(ItemDTO::getOrderItemId).collect(Collectors.toList());
    }

    private void getOrderList(List<ItemDTO> list, Integer pageNum, String cookie) {
        HashMap<String, String> param = new HashMap<>();
        param.put("pageSize", 50 + "");
        param.put("pageNo", pageNum + "");
        Map<String, String> header = new HashMap<>();
        header.put("cookie", cookie);
        String s = CommHttpUtils.doGet(listUrl, param, header);
        OrderDTO orderDTO = JSONObject.parseObject(s, OrderDTO.class);
        if (orderDTO.getSuccess()) {
            List<ItemDTO> items = orderDTO.getResults().getItems();
            if (!items.isEmpty()) {
                list.addAll(items);
                getOrderList(list, pageNum + 1, cookie);
            }
        }
    }

    public DingXiangOrderDTO getOrderDetail(String cookie, String itemId) {
        Map<String, String> header = new HashMap<>();
        header.put("cookie", cookie);
        HashMap<String, String> param = new HashMap<>();
        param.put("orderItemId", itemId);
        String result = CommHttpUtils.doGet(detailUrl, param, header);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        DingXiangOrderItemDTO dingXiangOrderItemDTO = JSONObject.parseObject(result, DingXiangOrderItemDTO.class);
        if (!dingXiangOrderItemDTO.getSuccess()) {
            return null;
        }
        return dingXiangOrderItemDTO.getResults();
    }

    public DingXiangReserveDTO getClientDetail(String cookie, String reserveOrderId) {
        Map<String, String> header = new HashMap<>();
        header.put("cookie", cookie);
        HashMap<String, String> clientParam = new HashMap<>();
        clientParam.put("id", reserveOrderId);
        String result = CommHttpUtils.doGet(clientUrl, clientParam, header);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        DingXiangReserveItemDTO dingXiangReserveItemDTO = JSONObject.parseObject(result, DingXiangReserveItemDTO.class);
        if (!dingXiangReserveItemDTO.getSuccess()) {
            return null;
        }
        return dingXiangReserveItemDTO.getResults();
    }

    public List<DingXiangItemInfoDTO> skuList(String cookie) {
        List<DingXiangItemInfoDTO> list = new ArrayList<>();
        getSkuList(list, 1, cookie);
        //获取itemid，通过itemid查询订单详情
        return list;
    }

    private void getSkuList(List<DingXiangItemInfoDTO> list, Integer pageNum, String cookie) {
        HashMap<String, String> param = new HashMap<>();
        param.put("pageSize", 50 + "");
        param.put("pageNo", pageNum + "");
        //param.put("serviceState", 1 + "");
        Map<String, String> header = new HashMap<>();
        header.put("cookie", cookie);
        String s = CommHttpUtils.doGet(skuUrl, param, header);
        DingXiangServiceInfoDTO serviceInfoDTO = JSONObject.parseObject(s, DingXiangServiceInfoDTO.class);
        if (serviceInfoDTO.getSuccess()) {
            List<DingXiangItemInfoDTO> items = serviceInfoDTO.getResults().getItems();
            if (!items.isEmpty()) {
                list.addAll(items);
                getSkuList(list, pageNum + 1, cookie);
            }
        }
    }
}
