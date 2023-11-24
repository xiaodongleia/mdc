package com.meerkat.vaccine.order.model.dto.DingXiang;

import lombok.Data;

import java.util.List;

/**
 * @author zxw
 * @date 2022/03/02 18:20
 */
@Data
public class DingXiangReserveDTO {

    private DingXiangReserveInfoDTO reserveInfo;
    private DingXiangReserveUserInfoDTO reserveUserInfo;
    private List<DingXiangOperationRecordDTO> operationRecordList;
}
