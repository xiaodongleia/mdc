package com.meerkat.vaccine.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meerkat.common.config.tencent.TencentCosConfig;
import com.meerkat.common.utils.DateUtils;
import com.meerkat.vaccine.order.domain.MdcOrderAppointDO;
import com.meerkat.vaccine.order.mapper.MdcOrderAppointMapper;
import com.meerkat.vaccine.order.mapper.MdcOrderMapper;
import com.meerkat.vaccine.order.model.excel.AppointRemindExcel;
import com.meerkat.vaccine.order.service.MdcOrderAppointService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 订单履约时间表 服务实现类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Slf4j
@Service
public class MdcOrderAppointServiceImpl extends ServiceImpl<MdcOrderAppointMapper, MdcOrderAppointDO> implements MdcOrderAppointService {

    @Autowired
    private MdcOrderMapper mdcOrderMapper;
    @Autowired
    private TencentCosConfig tencentCosConfig;


    @Override
    public String doAppointRemindJob() {
        //获取明天的时间
        Date runDate = new Date();
        Date date = DateUtils.addDay(runDate, 1);
        String today = DateUtils.dateYmdFormatString(runDate);
        String tomorrow = DateUtils.dateYmdFormatString(date);

        //获取第二针和第三针的订单id信息
        LambdaQueryWrapper<MdcOrderAppointDO> orderAppointQueryWrapper = new LambdaQueryWrapper<>();
        orderAppointQueryWrapper.select(MdcOrderAppointDO::getOrderSplitId, MdcOrderAppointDO::getSecondAppointTime, MdcOrderAppointDO::getThirdAppointTime, MdcOrderAppointDO::getSource)
                .eq(MdcOrderAppointDO::getSecondAppointTime, tomorrow).or().eq(MdcOrderAppointDO::getThirdAppointTime, tomorrow);
        List<MdcOrderAppointDO> mcrmOrderAppointDOList = this.list(orderAppointQueryWrapper);
        if (mcrmOrderAppointDOList.isEmpty()) {
            log.info("暂未发现需要通知的订单");
            log.info("结束执行定时任务时间: {}", new Date());
            return null;
        }
        HashSet<String> orderIds = new HashSet<>();
        mcrmOrderAppointDOList.forEach(o -> orderIds.add(o.getOrderSplitId()));

        List<AppointRemindExcel> appointRemindExcelList = mdcOrderMapper.listAppointRemindExcel(orderIds);
        for (AppointRemindExcel appointRemindExcel : appointRemindExcelList) {
            for (MdcOrderAppointDO mcrmOrderAppointDO : mcrmOrderAppointDOList) {
                if (appointRemindExcel.getOrderSplitId().equals(mcrmOrderAppointDO.getOrderSplitId())
                        && appointRemindExcel.getSource().equals(mcrmOrderAppointDO.getSource())) {
                    appointRemindExcel.setSecondAppointTime(mcrmOrderAppointDO.getSecondAppointTime());
                    appointRemindExcel.setThirdAppointTime(mcrmOrderAppointDO.getThirdAppointTime());
                }
            }
        }

        //写出表格
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "订单通知"), AppointRemindExcel.class, appointRemindExcelList);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
            return tencentCosConfig.uploadInputStream(byteArrayInputStream, today + ".xls", out.size());

        } catch (Exception e) {
            log.error("订单通知文件失败");
            return null;
        }
    }
}
