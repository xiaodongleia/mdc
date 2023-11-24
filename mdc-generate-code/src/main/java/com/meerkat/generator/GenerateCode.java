package com.meerkat.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 主程序
 *
 * @author zhujx
 * @date 2021/11/25 18:29
 */
public class GenerateCode {

    public static void main(String[] args) {

        //Ip:Port/database
        String database = "localhost:3306/mdc";
        //数据库账号
        String username = "root";
        //数据库密码
        String password = "000000";
        //生成的表
        String[] strings = {
                "sys_kv",
                "mdc_client",
                "mdc_client_order",
                "mdc_order",
                "mdc_order_appoint",
                "ods_bai_du_client",
                "ods_bai_du_order",
                "ods_ding_xiang_client",
                "ods_ding_xiang_order",
                "ods_excel_order"
        };
        //父包名
        String parent = "com.meerkat." + "vaccine.order";

        String outputDir = System.getProperty("user.dir") + "/mdc-vaccine-order" + "/src/main/java";

        String url = "jdbc:mysql://" + database + "?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&useUnicode=true";

        String mapperPath = outputDir + "/" + parent.replace(".", "/") + "/mapper/xml";

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("朱家兴") // 设置作者
                            .enableSwagger() //开启swagger模式
                            .disableOpenDir() // 不打开文件夹
                            .fileOverride() // 覆盖
                            .dateType(DateType.ONLY_DATE) //日期格式
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parent) // 设置父包名
                            .entity("domain") //设置实体类的包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperPath)); // 设置mapperXml生成路径
                })

                .strategyConfig(builder -> {
                    builder.addInclude(strings)
                            .entityBuilder()
                            .enableLombok() //开启lombok模式
                            .idType(IdType.AUTO) //自增类型
                            .formatFileName("%sDO") //文件名格式
                            .build()
                            .controllerBuilder()
                            .enableRestStyle() //开启RestController注解
                            .formatFileName("%sController") //文件名格式
                            .build()
                            .serviceBuilder()
                            .formatServiceFileName("%sService") //文件名格式
                            .formatServiceImplFileName("%sServiceImpl") //文件名格式
                            .build()
                            .mapperBuilder()
                            .formatMapperFileName("%sMapper") //文件名格式
                            .enableMapperAnnotation() //开启@Mapper注解
                            .formatXmlFileName("%sMapper") //文件名格式
                            .build();
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
