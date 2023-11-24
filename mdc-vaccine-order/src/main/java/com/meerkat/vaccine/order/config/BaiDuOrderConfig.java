package com.meerkat.vaccine.order.config;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.common.utils.CommHttpUtils;
import com.meerkat.vaccine.order.domain.OdsBaiDuOrderDO;
import com.meerkat.vaccine.order.model.dto.baidu.OrderDetailDTO;
import com.meerkat.vaccine.order.model.dto.baidu.SkuItemDTO;
import com.meerkat.vaccine.order.util.SignOptions;
import com.meerkat.vaccine.order.util.SignUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 百度config
 *
 * @author zhujx
 * @date 2022/03/05 10:44
 */
@Data
@Component
@ConfigurationProperties(prefix = "order.config.baidu")
public class BaiDuOrderConfig {

    private static final String BCE_AUTH_VERSION = "bce-auth-v1";
    private static final String BCE_PREFIX_LOWER_CASE = "x-bce-";
    private static final String SIGNATURE_HEADER_NAME = "X-Bce-Signature";
    private static final String ONLINE_USELESS_PATH = "/seller-open";
    private static final Set<String> DEFAULT_HEADERS_TO_SIGN = new HashSet<>(Arrays.asList("host", "x-bce-date"));
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC);
    private static final String HEADER_JOINER = "\n";
    private static final String QUERY_STRING_JOINER = "&";
    private static final String SIGNED_HEADER_STRING_JOINER = ";";
    private static final String HTTP_METHOD = "POST";

    /**
     * appKey
     */
    private String appKey;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * 订单列表
     */
    private String orderListUrl;

    /**
     * 订单详情
     */
    private String orderDetailUrl;

    /**
     * SKU
     */
    private String orderSkuUrl;


    public Set<String> toGetOrderList() {

        Set<String> list = new HashSet<>();

        String time = DATE_TIME_FORMATTER.format(new Date().toInstant());
        URI uri = URI.create(orderListUrl);

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", uri.getHost());
        headers.put("x-bce-date", time);

        SignOptions options = new SignOptions();
        options.setTimestamp(time);
        options.setHeadersToSign(new HashSet<>(Arrays.asList("host", "x-bce-date")));

        sign(uri, headers, options);

        toGetOrderList(headers, list, 0L);

        return list;
    }

    public void toGetOrderList(Map<String, String> headers, Set<String> list, Long lastId) {
        JSONObject page = new JSONObject();
        page.put("desc", "false");
        page.put("last", lastId);
        page.put("orderBy", 1);
        page.put("size", 50);
        JSONObject data = new JSONObject();
        data.put("page", page);
        data.put("type", 2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        String result = CommHttpUtils.doPostJson(orderListUrl, jsonObject.toJSONString(), headers);
        JSONObject json = JSONObject.parseObject(result);
        if (!"0".equals(json.getString("status"))) {
            return;
        }
        List<OdsBaiDuOrderDO> newList = json.getJSONObject("data").getJSONArray("data").toJavaList(OdsBaiDuOrderDO.class);
        if (newList.isEmpty()) {
            return;
        }
        list.addAll(newList.stream().map(OdsBaiDuOrderDO::getOrderId).collect(Collectors.toSet()));
        toGetOrderList(headers, list, newList.get(newList.size() - 1).getId());
    }

    public OrderDetailDTO toGetOrderDetail(String orderId) {
        String time = DATE_TIME_FORMATTER.format(new Date().toInstant());
        URI uri = URI.create(orderDetailUrl);

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", uri.getHost());
        headers.put("x-bce-date", time);

        SignOptions options = new SignOptions();
        options.setTimestamp(time);
        options.setHeadersToSign(new HashSet<>(Arrays.asList("host", "x-bce-date")));

        sign(uri, headers, options);

        JSONObject data = new JSONObject();
        data.put("orderId", orderId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        String s = CommHttpUtils.doPostJson(orderDetailUrl, jsonObject.toJSONString(), headers);
        OrderDetailDTO orderDetailDTO = JSONObject.parseObject(s, OrderDetailDTO.class);
        return orderDetailDTO;
    }

    public SkuItemDTO toGetOrderSku(Long skuId, Long storeId) {
        String time = DATE_TIME_FORMATTER.format(new Date().toInstant());
        URI uri = URI.create(orderSkuUrl);

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", uri.getHost());
        headers.put("x-bce-date", time);

        SignOptions options = new SignOptions();
        options.setTimestamp(time);
        options.setHeadersToSign(new HashSet<>(Arrays.asList("host", "x-bce-date")));

        sign(uri, headers, options);

        JSONObject data = new JSONObject();
        data.put("skuId", skuId);
        data.put("storeId", storeId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        String s = CommHttpUtils.doPostJson(orderSkuUrl, jsonObject.toJSONString(), headers);
        SkuItemDTO skuItemDTO = JSONObject.parseObject(s, SkuItemDTO.class);
        return skuItemDTO;
    }


    private void sign(URI uri, Map<String, String> headers, SignOptions options) {
        Map<String, String> httpHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        httpHeaders.putAll(headers);

        String authString = BCE_AUTH_VERSION + "/" + appKey + "/" + options.getTimestamp() + "/" + options.getExpirationInSeconds();

        String signingKey = SignUtils.sha256Hex(appSecret, authString);

        String canonicalUri = this.getCanonicalUriPath(uri.getPath());

        SortedMap<String, String> headersToSign = getHeadersToSign(httpHeaders, options.getHeadersToSign());
        String canonicalHeader = this.getCanonicalHeaders(headersToSign);
        String signedHeaders = "";
        if (options.getHeadersToSign() != null) {
            signedHeaders = String.join(SIGNED_HEADER_STRING_JOINER, headersToSign.keySet());
            signedHeaders = signedHeaders.trim().toLowerCase();
        }

        String canonicalRequest = String.join(HEADER_JOINER, HTTP_METHOD, canonicalUri, "", canonicalHeader);

        String signature = SignUtils.sha256Hex(signingKey, canonicalRequest);

        String signatureHeaderValue = authString + "/" + signedHeaders + "/" + signature;

        headers.put(SIGNATURE_HEADER_NAME, signatureHeaderValue);
    }


    private String getCanonicalUriPath(String path) {
        if (path == null) {
            return "/";
        } else if (path.startsWith("/")) {
            return SignUtils.normalizePath(path.replace(ONLINE_USELESS_PATH, ""));
        } else {
            return "/" + SignUtils.normalizePath(path);
        }
    }

    private String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        }

        List<String> headerStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            String value = entry.getValue();
            if (value == null || value.isEmpty()) {
                throw new RuntimeException("Header to sign should have non-empty value.");
            }
            headerStrings.add(SignUtils.normalize(key.trim().toLowerCase()) + ':' + SignUtils.normalize(value.trim()));
        }
        Collections.sort(headerStrings);

        return String.join(HEADER_JOINER, headerStrings);
    }

    private SortedMap<String, String> getHeadersToSign(Map<String, String> headers, Set<String> headersToSign) {
        SortedMap<String, String> ret = new TreeMap<>();
        if (headersToSign != null) {
            Set<String> tempSet = new HashSet<>();
            for (String header : headersToSign) {
                tempSet.add(header.trim().toLowerCase());
            }
            headersToSign = tempSet;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if ((headersToSign == null && this.isDefaultHeaderToSign(key))
                        || (headersToSign != null && headersToSign.contains(key.toLowerCase())
                        && !SIGNATURE_HEADER_NAME.equalsIgnoreCase(key))) {
                    ret.put(key, entry.getValue());
                }
            }
        }
        return ret;
    }

    private boolean isDefaultHeaderToSign(String header) {
        header = header.trim().toLowerCase();
        return header.startsWith(BCE_PREFIX_LOWER_CASE) || DEFAULT_HEADERS_TO_SIGN.contains(header);
    }


}
