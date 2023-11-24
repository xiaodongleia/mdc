package com.meerkat.common.config.tencent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * cos配置信息
 *
 * @author zhujx
 * @date 2022/01/18 16:26
 */
@Data
@ApiModel(value = "CosVO", description = "cos配置信息")
public class CosVO {

    @ApiModelProperty("secretId")
    private String secretId;

    @ApiModelProperty("secretKey")
    private String secretKey;

    @ApiModelProperty("地域")
    private String sessionToken;

    @ApiModelProperty("bucket名称")
    private String bucketName;

    @ApiModelProperty("文件夹名称")
    private String fileDir;

    @ApiModelProperty("请求时需要用的 token 字符串")
    private String regionName;

    @ApiModelProperty("密钥的起始时间")
    private Long startTime;

    @ApiModelProperty("密钥的失效时间")
    private Long expiredTime;

}
