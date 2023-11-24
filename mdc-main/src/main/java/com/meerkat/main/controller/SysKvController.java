package com.meerkat.main.controller;


import com.meerkat.common.api.BaseResult;
import com.meerkat.common.utils.CopyUtil;
import com.meerkat.vaccine.order.domain.SysKvDO;
import com.meerkat.vaccine.order.model.param.SysKvParam;
import com.meerkat.vaccine.order.model.vo.SysKvVO;
import com.meerkat.vaccine.order.service.SysKvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 键值表 前端控制器
 * </p>
 *
 * @author 朱家兴
 * @since 2022-03-10
 */
@Api(tags = "键值维护")
@RestController
@RequestMapping("/sys/kv")
public class SysKvController {


    @Autowired
    private SysKvService sysKvService;

    @ApiOperation("插入键值数据")
    @PostMapping("/add")
    public BaseResult<Void> add(@RequestBody SysKvParam sysKvParam) {
        BaseResult<Void> result = new BaseResult<>();
        boolean add = sysKvService.add(sysKvParam.getK(), sysKvParam.getV());
        return add ? result : result.failed("异常错误，请联系管理员");
    }

    @ApiOperation("列表查询")
    @GetMapping("/list")
    public  BaseResult<List<SysKvVO>> list(){
        BaseResult<List<SysKvVO>> result = new BaseResult<>();
        List<SysKvDO> sysKvDOS = sysKvService.loadAll();

        List<SysKvVO> sysKvVOS = CopyUtil.copyList(sysKvDOS, SysKvVO.class);
        return result.success(sysKvVOS);
    }

}
