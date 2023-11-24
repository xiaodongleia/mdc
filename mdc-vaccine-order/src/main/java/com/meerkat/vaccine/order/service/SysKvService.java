package com.meerkat.vaccine.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meerkat.vaccine.order.domain.SysKvDO;

import java.util.List;

/**
 * <p>
 * 键值表 服务类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
public interface SysKvService extends IService<SysKvDO> {

    /**
     * 根据k获取v
     *
     * @param k 键
     * @return java.lang.String
     * @author zhujx
     * @date 2022/3/10 19:12
     */
    String getValueByKey(String k);

    /**
     * 根据k模糊搜索
     *
     * @param k 键
     * @return java.util.List<com.meerkat.vaccine.order.domain.SysKvDO>
     * @author zhujx
     * @date 2022/4/7 10:36
     */
    List<SysKvDO> getValueLikeKey(String k);

    /**
     * 添加键值
     *
     * @param k 键
     * @param v 值
     * @return boolean
     * @author zhujx
     * @date 2022/3/11 10:06
     */
    boolean add(String k, String v);

    /**
     * 根据k修改
     *
     * @param k 键
     * @param v 值
     * @return boolean
     * @author zhujx
     * @date 2022/3/11 10:08
     */
    boolean updateByKey(String k, String v);

    /**
     * 查询所有
     *
     * @param
     * @return java.util.List<com.meerkat.vaccine.order.domain.SysKvDO>
     * @author zxw
     * @date 2023/3/10 2023/3/10
     */
    List<SysKvDO> loadAll();
}
