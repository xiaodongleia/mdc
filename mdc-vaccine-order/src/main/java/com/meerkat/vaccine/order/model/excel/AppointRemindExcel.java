package com.meerkat.vaccine.order.model.excel;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

/**
 * 预约提醒表格
 *
 * @author zhujx
 * @date 2022/03/11 16:23
 */
@Data
@ExcelTarget("AppointRemindExcel")
public class AppointRemindExcel implements java.io.Serializable {

    @Excel(name = "商品名称")
    private String goodsName;

    @Excel(name = "订单编号")
    private String orderSplitId;

    @Excel(name = "渠道")
    private String source;

    @Excel(name = "客户姓名")
    private String clientName;

    @Excel(name = "身份证号")
    private String idCard;

    @Excel(name = "预约人手机号")
    private String phoneNo;

    @Excel(name = "预约时间二")
    private String secondAppointTime;

    @Excel(name = "预约时间三")
    private String thirdAppointTime;
}
