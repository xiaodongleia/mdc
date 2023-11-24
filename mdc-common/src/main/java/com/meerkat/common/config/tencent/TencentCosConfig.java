package com.meerkat.common.config.tencent;

import com.meerkat.common.api.BaseBizCodeEnum;
import com.meerkat.common.exception.BizException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

/**
 * 腾讯云COS工具类
 *
 * @author zhujx
 * @date 2022/01/17 13:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.tencent.cos")
public class TencentCosConfig {

    /**
     * log日志
     */
    private static Logger logger = LoggerFactory.getLogger(TencentCosConfig.class);
    /**
     * secretId
     */
    private String secretId;
    /**
     * secretKey
     */
    private String secretKey;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 文件夹名称
     */
    private String fileDir;
    /**
     * 地域
     */
    private String regionName;
    /**
     * 访问域名
     */
    private String baseUrl;

    public CosVO getConfig() {

        TreeMap<String, Object> config = new TreeMap<>();
        // 云 api 密钥 SecretId
        config.put("secretId", secretId);
        // 云 api 密钥 SecretKey
        config.put("secretKey", secretKey);
        // 临时密钥有效时长，单位是秒
        config.put("durationSeconds", 1800);
        // 换成你的 bucket
        config.put("bucket", bucketName);
        // 换成 bucket 所在地区
        config.put("region", regionName);
        // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
        config.put("allowPrefixes", new String[]{
                fileDir + "/*"
        });
        // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
        String[] allowActions = new String[]{
                // 简单上传
                "name/cos:PutObject",
                "name/cos:PostObject",
                // 分片上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
        };
        config.put("allowActions", allowActions);

        Response response;
        try {
            response = CosStsClient.getCredential(config);
        } catch (IOException e) {
            logger.error("获取临时密钥失败 {}", e.getMessage(), e);
            throw new BizException(BaseBizCodeEnum.FAILED.getCode(), "获取临时密钥失败");
        }

        CosVO cosVO = new CosVO();
        cosVO.setSecretId(response.credentials.tmpSecretId);
        cosVO.setSecretKey(response.credentials.tmpSecretKey);
        cosVO.setSessionToken(response.credentials.sessionToken);
        cosVO.setStartTime(response.startTime);
        cosVO.setExpiredTime(response.expiredTime);
        cosVO.setBucketName(bucketName);
        cosVO.setFileDir(fileDir);
        cosVO.setRegionName(regionName);
        return cosVO;
    }

    /**
     * 创建 COSClient 实例，这个实例用来后续调用请求
     */
    public COSClient createCosClient() {
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();
        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(regionName));
        // 设置请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 以下的设置，是可选的：
        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30 * 1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30 * 1000);
        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public String uploadInputStream(InputStream inputStream, String fileName, Integer contentLength) {

        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = createCosClient();

        // 对象键(Key)是对象在存储桶中的唯一标识。
        String key = fileDir + "/" + fileName;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (contentLength != null) {
            objectMetadata.setContentLength(contentLength);
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        cosClient.putObject(putObjectRequest);

        return baseUrl + key;
    }

    public void uploadInputStreamOnClient(COSClient cosClient, InputStream inputStream, String fileName, int contentLength) {

        // 对象键(Key)是对象在存储桶中的唯一标识。
        String key = fileDir + "/" + fileName;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        cosClient.putObject(putObjectRequest);
    }


}
