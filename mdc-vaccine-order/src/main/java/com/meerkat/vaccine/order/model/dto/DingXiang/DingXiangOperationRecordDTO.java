package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

import java.util.Date;

/**
 *  操作记录
 *
 * @author zxw
 * @date 2022/03/02 18:30
 */
@Data
public class DingXiangOperationRecordDTO {

    //操作时间
    private Date date;

    //类型
    private String type;

    //内容
    private String content;

    //操作者
    private String operator;
}
