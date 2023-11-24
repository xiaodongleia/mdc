package com.meerkat.main.controller;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.meerkat.common.api.BaseResult;
import com.meerkat.common.exception.BizException;
import com.meerkat.vaccine.order.adapter.hander.BaiDuHandlerAdapter;
import com.meerkat.vaccine.order.adapter.hander.BaseExcelSourceHandlerAdapter;
import com.meerkat.vaccine.order.adapter.hander.BaseRetailExcelSourceHandlerAdapter;
import com.meerkat.vaccine.order.adapter.hander.DingXiangHandlerAdapter;
import com.meerkat.vaccine.order.domain.MdcOrderDO;
import com.meerkat.vaccine.order.domain.OdsExcelOrderDO;
import com.meerkat.vaccine.order.handel.FileHandler;
import com.meerkat.vaccine.order.model.enums.SourceSystemEnum;
import com.meerkat.vaccine.order.model.vo.OrderUpdateReqParam;
import com.meerkat.vaccine.order.service.MdcOrderService;
import com.meerkat.vaccine.order.util.LocalMemory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 开放接口
 *
 * @author zhujx
 * @date 2022/04/07 15:05
 */
@Slf4j
@Api(tags = "开放接口")
@RestController
@RequestMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
public class VaccineOrderController {

    @Autowired
    private BaseExcelSourceHandlerAdapter baseExcelSourceHandlerAdapter;

    @Autowired
    private BaseRetailExcelSourceHandlerAdapter baseRetailExcelSourceHandlerAdapter;

    @Autowired
    private MdcOrderService mdcOrderService;

    @Autowired
    private BaiDuHandlerAdapter baiDuHandlerAdapter;

    @Autowired
    private DingXiangHandlerAdapter dingXiangHandlerAdapter;

    @ApiOperation("表格导入")
    @PostMapping(value = "/excel/import/{source}")
    public BaseResult<OrderUpdateReqParam> notice(@RequestParam("file") MultipartFile file,@PathVariable("source") String source) {
//        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
//        String source = request.getParameter("source");
//        String source = JSONObject.parseObject(sourceJson).getString("source");
        log.info("import-source:{}", source);
        BaseResult<OrderUpdateReqParam> result = new BaseResult<>();

        //原文件数据打印查询
        /*try {
            List<Map<String, Object>> data = ExcelUtil.getReader(file.getInputStream()).readAll();
            for (Map map : data) {
                System.out.println(JSONObject.toJSONString(map));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        FileHandler.set(file);
        baseExcelSourceHandlerAdapter.obtainData(source);
        OrderUpdateReqParam requestVO = baseExcelSourceHandlerAdapter.clearOrder(source);
        return result.success(requestVO);
    }



    @ApiOperation("实物订单表格导入")
    @PostMapping(value = "/retail/import/{source}")
    public BaseResult<OrderUpdateReqParam> reatil(@RequestParam("file") MultipartFile file,@PathVariable("source") String source) throws Exception {
//        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
//        String source = request.getParameter("source");
//        String source = JSONObject.parseObject(sourceJson).getString("source");
        log.info("import-source:{}", source);
        BaseResult<OrderUpdateReqParam> result = new BaseResult<>();

        FileHandler.set(file);
        List<OdsExcelOrderDO> odsExcelOrderDOS = null;
        odsExcelOrderDOS = baseRetailExcelSourceHandlerAdapter.obtainRetailData(source);
        OrderUpdateReqParam requestVO = baseRetailExcelSourceHandlerAdapter.clearOrder(odsExcelOrderDOS,source);
        return result.success(requestVO);
    }


    @ApiOperation("存储数据回调")
    @PostMapping(value = "/affirm")
    public BaseResult<Void> affirm(String requestId, Boolean affirmResult) {
        log.info("requestId:{},affirmResult:{}",requestId,affirmResult);
        mdcOrderService.saveOrder(affirmResult, requestId);
        return new BaseResult<>();
    }

    @ApiOperation("初始化时间标签")
    @GetMapping(value = "/getExamWithinDays")
    public BaseResult<Void> getExamWithinDays() {
        mdcOrderService.saveExamWithinDays();
        return new BaseResult<>();
    }

    @ApiOperation("缓存查看-测试")
    @PostMapping(value = "/test")
    public BaseResult<List<MdcOrderDO>> affirm(String requestId) {
        List<MdcOrderDO> mdcOrderDOS = LocalMemory.get(requestId);
        return new BaseResult<List<MdcOrderDO>>().success(mdcOrderDOS);
    }

    @ApiOperation("缓存清空-测试")
    @PostMapping(value = "/clear")
    public BaseResult<Void> clear() {
        LocalMemory.removeAll();
        return new BaseResult<Void>().success();
    }

    @ApiOperation("测试-手动清洗百度数据")
    @PostMapping(value = "/test-baidu")
    public BaseResult<Void> testBaidu() {
        baiDuHandlerAdapter.obtainData(SourceSystemEnum.BAI_DU.getName());
        baiDuHandlerAdapter.clearClient();
        baiDuHandlerAdapter.clearOrder(SourceSystemEnum.BAI_DU.getName());
        return new BaseResult<Void>().success();
    }

    @ApiOperation("测试-手动清洗丁香园数据")
    @PostMapping(value = "/test-dx")
    public BaseResult<Void> testDx() {
        dingXiangHandlerAdapter.getSku();
        dingXiangHandlerAdapter.obtainData(SourceSystemEnum.DING_XIANG.getName());
        dingXiangHandlerAdapter.clearClient();
        dingXiangHandlerAdapter.clearOrder(SourceSystemEnum.DING_XIANG.getName());
        return new BaseResult<Void>().success();
    }

    @ApiOperation("测试-手动清洗丁香园数据")
    @PostMapping(value = "/test-dx-sku")
    public BaseResult<Void> testSku() {
        dingXiangHandlerAdapter.getSku();
        return new BaseResult<Void>().success();
    }



}
