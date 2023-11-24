package com.meerkat.notice.adapter;

/**
 * 渠道适配器
 *
 * @author zhujx
 * @date 2022/03/03 13:40
 */
public interface RobotNoticeAdapter {

    /**
     * 推送类型
     *
     * @return Integer
     * @author zhujx
     * @date 2022/3/14 14:36
     */
    Integer getType();

    /**
     * 对比
     *
     * @param type type
     * @return boolean
     * @author zhujx
     * @date 2022/3/30 16:23
     */
    boolean supports(Integer type);

    /**
     * 通知
     *
     * @param body body
     * @author zhujx
     * @date 2022/3/30 16:23
     */
    void notice(String body);
}
