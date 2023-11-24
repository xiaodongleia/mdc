package com.meerkat.vaccine.order.util;

import com.meerkat.vaccine.order.domain.MdcOrderDO;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxw
 * @date 2022/04/12 16:18
 */
@Slf4j
public class LocalMemory {

    static Map<String, SoftReference<List<MdcOrderDO>>> localData = new ConcurrentHashMap<>();

    public static final int MAX_SIZE = 10000;

    public static final int WARN_VALUE = 8000;

    public static void put(String key, List<MdcOrderDO> data) {

        if (localData.size() >= WARN_VALUE) {
            log.warn("注意:本地缓存已经达到临界值,size:" + localData.size());
        }
        if (localData.size() > MAX_SIZE) {
            log.error("超出最大值:" + localData.size());
            return;
        }
        SoftReference<List<MdcOrderDO>> refCacheData = new SoftReference<>(data);
        localData.put(key, refCacheData);
    }

    public static List<MdcOrderDO> get(String key) {
        SoftReference<List<MdcOrderDO>> cacheDataSoftReference = localData.get(key);
        return cacheDataSoftReference.get();
    }

    public static void remove(String key) {
        localData.remove(key);
    }

    public static void removeAll(){
        localData.clear();
    }
}
