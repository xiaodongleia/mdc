package com.meerkat.vaccine.order.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2022/03/03 16:22
 */
public enum OrderStatusEnum {

    //丁香
    DX_CREATE(-1, 101,"创建订单,但未预约","待支付","丁香园"),
    DX_BEING(21, 102,"订单进行中","待预约","丁香园"),
    DX_CLOSE(60, 103,"订单关闭","交易关闭","丁香园"),
    DX_DONE(62, 104,"订单完成","交易完成","丁香园"),
    DX_START_REFUND(100,105, "开始退款","退款中","丁香园"),
    DX_END_REFUND(200,106, "退款完成","退款成功","丁香园"),

    //医鹿
    YL_CREATE(1001,101,"待支付","待支付","医鹿"),
    YL_CLOSE(1002,103,"交易关闭","交易关闭","医鹿"),
    YL_PAY_SUC(1003,102,"支付成功","待预约","医鹿"),
    YL_REFUNDING(1004,105,"退款中","退款中","医鹿"),
    YL_REFUND_SUC(1005,106,"退款成功","退款成功","医鹿"),
    YL_DONE(1006,104,"交易完成","交易完成","医鹿"),
    YL_REFUND_FAIL(1007,107,"退款失败","退款失败","医鹿"),
    //退款状态
    YL_REFUND_CLOSE(1101,103,"已关闭","交易关闭","医鹿"),
    YL_REFUND_DEAL(1102,105,"退款处理中","退款中","医鹿"),
    YL_REFUND(1103,106,"退款成功","退款成功","医鹿"),
    YL_REFUND_REFUSE(1104,102,"已拒绝","待预约","医鹿"),


    //支付宝
    ALIPAY_CREATE(1001,101,"待支付","待支付","支付宝"),
    ALIPAY_CLOSE(1002,103,"交易关闭","交易关闭","支付宝"),
    ALIPAY_PAY_SUC(1003,102,"支付成功","待预约","支付宝"),
    ALIPAY_REFUNDING(1004,105,"退款中","退款中","支付宝"),
    ALIPAY_REFUND_SUC(1005,106,"退款成功","退款成功","支付宝"),
    ALIPAY_DONE(1006,104,"交易完成","交易完成","支付宝"),
    ALIPAY_REFUND_FAIL(1007,107,"退款失败","退款失败","支付宝"),
    //退款状态
    ALIPAY_REFUND_CLOSE(1101,103,"已关闭","交易关闭","支付宝"),
    ALIPAY_REFUND_DEAL(1102,105,"退款处理中","退款中","支付宝"),
    ALIPAY_REFUND(1103,106,"退款成功","退款成功","支付宝"),
    ALIPAY_REFUND_REFUSE(1104,102,"已拒绝","待预约","支付宝"),

    //百度健康
    BD_APPOINT(140,102,"待确认预约","待预约","百度健康"),
    BD_WAIT_APPOINT(142,102,"待确认预约","待预约","百度健康"),
    BD_CLOSE(130,103,"交易关闭","交易关闭","百度健康"),
    BD_DEAL_SUC(201,104,"交易成功","交易完成","百度健康"),
    BD_DEAL_LINE_SUC(200,104,"交易成功","交易完成","百度健康"),
    BD_DEAL_LINE_SUC_OTHER(220,104,"交易成功","交易完成","百度健康"),
    BD_REFUND_CHECK(160,105,"待退款审核","退款中","百度健康"),
    BD_REFUNDING(170,105,"退款中","退款中","百度健康"),
    BD_REFUND_FAIL(181,107,"退款失败","退款失败","百度健康"),
    BD_REFUND_SUC(180,106,"退款成功","退款成功","百度健康"),
    BD_APPRAISE(5300,104,"用户已评价","交易完成","百度健康"),

    //亿家
    YI_JIA_APPOINT(1505,102,"支付成功","支付成功","亿家"),
    YI_JIA_SUC(1501,104,"交易完成","交易完成","亿家"),
    YI_JIA_REFUNDING(1502,105,"退款中","退款中","亿家"),
    YI_JIA_REFUND_FAIL(1503,107,"退款失败","退款失败","亿家"),
    YI_JIA_REFUND_SUC(1504,106,"退款成功","退款成功","亿家"),

    //京东
    JD_ORDER_DONE(2001,104,"完成","交易完成","京东"),
    JD_WAIT(2002,102,"等待确认收货","待预约","京东"),
    JD_NONE_PAY(2003,108,"未付款","待付款","京东"),
    JD_CANCEL(2004,103,"已取消","交易关闭","京东"),
    WAIT_REPEICT(2005,106,"(删除)等待确认收货","退款成功","京东"),
    WAIT_STORAGE(2006,106,"(删除)等待出库","退款成功","京东"),
    NOT_PAYMENT(2007,103,"(删除)未付款","交易关闭","京东"),
    LOCK_STORAGE(2008,102,"(锁定)等待出库","待预约","京东"),
    DEL_WAIT_PAYMENT(2009,103,"(删除)等待付款","交易关闭","京东"),
    WAIT_PAYMENT(2009,103,"等待付款","待支付","京东"),
    WAIT_PAY_CONFIRM(20010,101,"等待付款确认","待支付","京东"),
    //拼多多
    PDD_NONE_PAY(3001,101,"待支付","待支付","拼多多"),
    PDD_NOT_SHIPENTS(3002,106,"未发货，退款成功","退款成功","拼多多"),
    PDD_SIGN_FOR(3003,102,"已发货，待签收","待预约","拼多多"),
    PDD_ALREADY_SHIPENTS(3004,106,"已发货，退款成功","退款成功","拼多多"),
    PDD_ALREADY_SIGN_FOR(3005,104,"已签收","交易完成","拼多多"),
    PDD_ALREADY_CANCEL(3006,103,"已取消","交易关闭","拼多多"),
    PDD_REFUND_SUC(3007,106,"退款成功","退款成功","拼多多"),


    //天猫
    TM_NONE_PAY(5001,101,"等待买家付款","待支付","天猫"),
    TM_WAIT_APPOINT(5002,102,"买家已付款","支付成功","天猫"),
    TM_WAIT_APPOINT2(5003,102,"卖家已发货，等待买家确认","支付成功","天猫"),
    TM_DEAL_SUC(5004,104,"交易成功","交易成功","天猫"),
    TM_ALREADY_CANCEL(5005,103,"交易关闭","交易关闭","天猫"),
    TM_WAIT_APPOINT3(5006,102,"买家已付款，等待卖家发货","支付成功","天猫"),

    //龙腾
    LT_NONE_PAY(8001,101,"等待买家付款","待支付","龙腾"),
    LT_WAIT_APPOINT(8002,102,"买家已付款","支付成功","龙腾"),
    LT_WAIT_APPOINT2(8003,102,"卖家已发货，等待买家确认","支付成功","龙腾"),
    LT_DEAL_SUC(8004,104,"交易成功","交易成功","龙腾"),
    LT_ALREADY_CANCEL(8005,103,"交易关闭","交易关闭","龙腾"),
    LT_WAIT_APPOINT3(8006,102,"买家已付款，等待卖家发货","支付成功","龙腾"),

    //瑞慈
    RC_NONE_PAY(7001,101,"等待买家付款","待支付","瑞慈"),
    RC_WAIT_APPOINT(7002,102,"买家已付款","支付成功","瑞慈"),
    RC_WAIT_APPOINT2(7003,102,"卖家已发货，等待买家确认","支付成功","瑞慈"),
    RC_DEAL_SUC(7004,104,"交易成功","交易成功","瑞慈"),
    RC_ALREADY_CANCEL(7005,103,"交易关闭","交易关闭","瑞慈"),
    RC_WAIT_APPOINT3(7006,102,"买家已付款，等待卖家发货","支付成功","瑞慈"),

    //协调部
    XTB_NONE_PAY(11001,101,"等待买家付款","待支付","协调部"),
    XTB_WAIT_APPOINT(11002,102,"买家已付款","支付成功","协调部"),
    XTB_WAIT_APPOINT2(11003,102,"卖家已发货，等待买家确认","支付成功","协调部"),
    XTB_DEAL_SUC(11004,104,"交易成功","交易成功","协调部"),
    XTB_ALREADY_CANCEL(11005,103,"交易关闭","交易关闭","协调部"),
    XTB_WAIT_APPOINT3(11006,102,"买家已付款，等待卖家发货","支付成功","协调部"),

    //碧桂园
    BGY_NONE_PAY(12001,101,"等待买家付款","待支付","碧桂园"),
    BGY_WAIT_APPOINT(12002,102,"买家已付款","支付成功","碧桂园"),
    BGY_WAIT_APPOINT2(12003,102,"卖家已发货，等待买家确认","支付成功","碧桂园"),
    BGY_DEAL_SUC(12004,104,"交易成功","交易成功","碧桂园"),
    BGY_ALREADY_CANCEL(12005,103,"交易关闭","交易关闭","碧桂园"),
    BGY_WAIT_APPOINT3(12006,102,"买家已付款，等待卖家发货","支付成功","碧桂园"),

    //美团
    MT_WAIT_APPOINT(6001,102,"未使用","支付成功","美团"),
    MT_DEAL_SUC(6002,104,"已使用","交易成功","美团"),
    MT_REFUND_SUC(6003,106,"已退款","退款成功","美团"),


    //直白
    ZB_NONE_PAY(5001,101,"等待买家付款","待支付","直白"),
    ZB_WAIT_APPOINT(5002,102,"买家已付款","支付成功","直白"),
    ZB_WAIT_APPOINT2(5003,102,"卖家已发货，等待买家确认","支付成功","直白"),
    ZB_DEAL_SUC(5004,104,"交易成功","交易成功","直白"),
    ZB_ALREADY_CANCEL(5005,103,"交易关闭","交易关闭","直白"),

    //有赞
    YZ_NONE_PAY(4001,101,"待付款","待支付","有赞"),
    YZ_SIGN_FOR(4002,102,"待发货","待预约","有赞"),
    YZ_NONE_VERIFICATION(4003,001,"已发货","已预约","有赞"),
    YZ_ALREADY_SIGN_FOR(4004,104,"交易完成","交易完成","有赞"),
    YZ_ALREADY_CANCEL(4005,103,"交易关闭","交易关闭","有赞"),
    YZ_REFUND_SUC(4006,106,"退款成功","退款成功","有赞"),
    YZ_REFUND_FAIL(4007,107,"退款失败","退款失败","有赞"),
    YZ_REFUND_CHECKING(4008,105,"退款中","退款中","有赞"),

    //抖音
    DY_NONE_PAY(4001,101,"待支付","待支付","抖音"),
    DY_SIGN_FOR(4002,102,"备货中","待预约","抖音"),
    DY_NONE_VERIFICATION(4003,001,"已发货","已预约","抖音"),
    DY_ALREADY_SIGN_FOR(4004,104,"已完成","交易完成","抖音"),
    DY_ALREADY_CANCEL(4005,103,"已关闭","交易关闭","抖音"),
    DY_REFUND_SUC(4006,106,"同意退款,退款成功","退款成功","抖音"),
    DY_REFUND_FAIL(4007,107,"同意退款,退款失败","退款失败","抖音"),
    DY_REFUND_CHECK(4008,105,"同意退款,退款中","退款中","抖音"),
    DY_REFUND_CHECKING(4009,105,"售后中","退款中","抖音"),
    ;

    //二方code
    private Integer code;
    //自己维护的code
    private Integer mycode;
    //二方名称
    private String name;
    //自己维护的名称
    private String alias;

    //名称缩写
    private String sourceName;

    public Integer getCode() {
        return code;
    }

    public Integer getMyCode() {
        return mycode;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getSourceName() {
        return sourceName;
    }

    private OrderStatusEnum(Integer code, Integer mycode, String name, String alias,String sourceName) {
        this.code = code;
        this.mycode = mycode;
        this.name = name;
        this.alias = alias;
        this.sourceName = sourceName;
    }

    public static String getNameByCode(Integer code) {
        for (OrderStatusEnum ele : values()) {
            if(ele.getCode().equals(code)) {
                return ele.getName();
            }
        }
        return null;
    }

    public static Integer getCodeByName(String name) {
        for (OrderStatusEnum ele : values()) {
            if(ele.getName().equals(name)) {
                return ele.getCode();
            }
        }
        return null;
    }

    public static List<Integer> getAllCode() {
        List<Integer> list = new ArrayList();
        for (OrderStatusEnum ele : values()) {
            list.add(ele.getCode());
        }
        return list;
    }

    public static Integer getmyCodeByCode(Integer code) {
        for (OrderStatusEnum ele : values()) {
            if(ele.getCode().equals(code)) {
                return ele.getMyCode();
            }
        }
        return null;
    }

    public static Integer getmyCodeByName(String name) {
        for (OrderStatusEnum ele : values()) {
            if(ele.getName().equals(name)) {
                return ele.getMyCode();
            }
        }
        return null;
    }

    public static Integer getSelfCodeByNameCodeAndCode(String sourceName,Integer code){
        for (OrderStatusEnum ele : values()) {
            if(ele.getSourceName().equals(sourceName) && ele.getCode().equals(code)) {
                return ele.getMyCode();
            }
        }
        return null;
    }

    public static Integer getCodeByNameAndSourceName(String sourceName,String name){

        for (OrderStatusEnum ele : values()) {
            if(ele.getSourceName().equals(sourceName) && ele.getName().equals(name)) {
                return ele.getCode();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getCodeByNameAndSourceName("京东","未付款"));
    }

}
