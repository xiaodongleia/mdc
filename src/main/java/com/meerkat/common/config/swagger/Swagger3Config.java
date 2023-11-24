package com.meerkat.common.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * swagger3配置信息
 *
 * @author zhujx
 */
@Data
@EnableOpenApi
@Configuration
@ConfigurationProperties(prefix = "springfox.documentation.swagger-ui")
public class Swagger3Config {


    /**
     * 名称
     */
    private String title;

    /**
     * 版本
     */
    private String version;

    /**
     * 说明
     */
    private String description;

    /**
     * 分组
     */
    private String groupName;

    /**
     * 扫描路径
     */
    private String basePackage;


    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

}
