package com.meerkat.common.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpclient工具类
 *
 * @author 朱家兴
 * @date 2019/10/21
 */
public class CommHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommHttpUtils.class);

    private final static int SUCCESS = 200;


    /**
     * 带参数和请求头的GET请求
     *
     * @param url    url
     * @param param  param
     * @param header header
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doGet(String url, Map<String, String> param, Map<String, String> header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (header != null) {
                for (String key : header.keySet()) {
                    httpGet.setHeader(key, header.get(key));
                }
            }
            // 执行请求
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断返回状态是否为200
            if (statusCode == SUCCESS) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                logger.warn("状态码:{}", statusCode);
                resultString = null;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }

        return resultString;
    }

    /**
     * 带参数GET请求
     *
     * @param url   url
     * @param param param
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doGet(String url, Map<String, String> param) {
        return doGet(url, param, null);
    }

    /**
     * 无参GET请求
     *
     * @param url url
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * application/x-www-form-urlencoded编码方式带参数和请求头的POST请求
     *
     * @param url    url
     * @param param  param
     * @param header header
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doPost(String url, Map<String, String> param, Map<String, String> header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加请求头信息
            if (header != null) {
                for (String key : header.keySet()) {
                    httpPost.setHeader(key, header.get(key));
                }
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断返回状态是否为200
            if (statusCode == SUCCESS) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                logger.warn("状态码:{}", statusCode);
                resultString = null;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }

        return resultString;
    }

    /**
     * application/x-www-form-urlencoded编码方式带参POST请求
     *
     * @param url   url
     * @param param param
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doPost(String url, Map<String, String> param) {
        return doPost(url, param, null);
    }

    /**
     * application/x-www-form-urlencoded编码方式无参POST请求
     *
     * @param url url
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * application/json编码方式带请求头POST请求
     *
     * @param url    url
     * @param json   json
     * @param header header
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doPostJson(String url, String json, Map<String, String> header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加请求头信息
            if (header != null) {
                for (String key : header.keySet()) {
                    httpPost.setHeader(key, header.get(key));
                }
            }
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == SUCCESS) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                logger.warn("状态码:{}", statusCode);
                resultString = null;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }

        return resultString;
    }

    /**
     * application/json编码方式POST请求
     *
     * @param url  url
     * @param json json
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/10/21
     */
    public static String doPostJson(String url, String json) {
        return doPostJson(url, json, null);
    }
}
