package com.meerkat.vaccine.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meerkat.vaccine.order.domain.SysKvDO;
import com.meerkat.vaccine.order.mapper.SysKvMapper;
import com.meerkat.vaccine.order.service.SysKvService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 键值表 服务实现类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-04-06
 */
@Service
public class SysKvServiceImpl extends ServiceImpl<SysKvMapper, SysKvDO> implements SysKvService {

    @Override
    public String getValueByKey(String k) {
        LambdaQueryWrapper<SysKvDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysKvDO::getK, k);
        SysKvDO one = this.getOne(queryWrapper);
        return one == null ? null : one.getV();
    }

    @Override
    public List<SysKvDO> getValueLikeKey(String k) {
        LambdaQueryWrapper<SysKvDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SysKvDO::getK, k);
        return this.list(queryWrapper);
    }

    @Override
    public boolean add(String k, String v) {
        LambdaQueryWrapper<SysKvDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysKvDO::getK, k);
        SysKvDO one = this.getOne(queryWrapper);
        if (one == null) {
            one = new SysKvDO();
        }
        one.setK(k);
        one.setV(v);
        boolean save;
        try {
            save = this.save(one);
        } catch (Exception e) {
            save = this.updateByKey(k, v);
        }
        return save;
    }

    @Override
    public boolean updateByKey(String k, String v) {
        LambdaUpdateWrapper<SysKvDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysKvDO::getV, v).eq(SysKvDO::getK, k);
        return this.update(updateWrapper);
    }

    @Override
    public List<SysKvDO> loadAll() {
        return this.list();
    }
}
