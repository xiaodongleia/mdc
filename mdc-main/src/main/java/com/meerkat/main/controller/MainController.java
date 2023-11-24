package com.meerkat.main.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 *
 * @author zhujx
 * @date 2022/04/06 17:50
 */
@Api(tags = "健康检查")
@RestController
public class MainController {
    

    @GetMapping("/isOK")
    public String hello() {
        return "Hello,world!";
    }


}
