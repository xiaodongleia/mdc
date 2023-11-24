package com.meerkat.main.controller;

import com.meerkat.common.api.BaseResult;
import com.meerkat.notice.adapter.RobotNoticeAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 开放接口
 *
 * @author zhujx
 * @date 2022/03/05 11:18
 */
@Api(tags = "开放接口")
@RestController
@RequestMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
public class RobotNoticeController {

    @Autowired
    private List<RobotNoticeAdapter> robotNoticeAdapterList;

    @ApiOperation("通知接口")
    @PostMapping("/notice/{type}")
    public BaseResult<Void> notice(@PathVariable("type") Integer type, @RequestBody String body) {
        BaseResult<Void> result = new BaseResult<>();
        RobotNoticeAdapter robotNoticeAdapter = getRobotNoticeAdapter(type);

        robotNoticeAdapter.notice(body);

        return result;
    }

    private RobotNoticeAdapter getRobotNoticeAdapter(Integer type) {
        if (type == null) {
            return null;
        }
        RobotNoticeAdapter robotNoticeAdapter = null;
        for (RobotNoticeAdapter adapter : robotNoticeAdapterList) {
            if (adapter.supports(type)) {
                robotNoticeAdapter = adapter;
                break;
            }
        }
        return robotNoticeAdapter;
    }

}

