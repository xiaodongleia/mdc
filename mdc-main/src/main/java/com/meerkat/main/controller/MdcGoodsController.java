package com.meerkat.main.controller;


import com.meerkat.common.api.BaseResult;
import com.meerkat.vaccine.order.adapter.hander.DingXiangHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxw
 * @since 2022-04-11
 */
@RestController
@RequestMapping(value = "/sku", produces = MediaType.APPLICATION_JSON_VALUE)
public class MdcGoodsController {

    @Autowired
    private DingXiangHandlerAdapter handlerAdapter;

    @GetMapping("/get")
    public BaseResult<Void> get() {
        handlerAdapter.getSku();
        return new BaseResult<>();
    }

}
