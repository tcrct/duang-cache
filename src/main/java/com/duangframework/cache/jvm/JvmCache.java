package com.duangframework.cache.jvm;

import com.duangframework.cache.core.CacheException;
import com.duangframework.cache.core.CacheKeyModel;
import com.duangframework.cache.core.ICache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JVM CACHE
 *
 * @author Laotang
 * @since 1.0
 */
public class JvmCache implements ICache<JvmCache> {

    private static final Map<String, Object> JVM_CACHE_MAP = new ConcurrentHashMap<>();
    private static JvmCache JVM_CACHE = new JvmCache();
    private CacheKeyModel model;

    public static JvmCache duang() {

        return JVM_CACHE;
    }

    private String key;
    private Object value;

    public JvmCache key(String key) {
        this.key = key;
        return this;
    }

    public JvmCache value(Object value) {
        this.value = value;
        return this;
    }

    public void set() {
        if (null == key) {
            throw new CacheException("设置缓存值时，[key]关键字不能为空");
        }
        if (null == value) {
            throw new CacheException("设置缓存值时，[value]值不能为空");
        }
        if (null == model) {
            model = new CacheKeyModel.Builder().customKey(key).build();
        }
        JVM_CACHE_MAP.put(model.getKey(), value);
    }

    @Override
    public JvmCache model(CacheKeyModel model) {
        this.model = model;
        return this;
    }

    @Override
    public <T> T get() {
        if (null ==key) {
            throw new CacheException("取缓存值时，[key]关键字不能为空");
        }
        return (T)JVM_CACHE_MAP.get(key);
    }

    @Override
    public long remove() {
        if (null ==key) {
            throw new CacheException("删除缓存时，[key]关键字不能为空");
        }
        try {
            JVM_CACHE_MAP.remove(key);
            return 1;
        } catch (Exception e) {
            throw new CacheException("删除缓存时出错: " + e.getMessage(), e);
        }
    }
}
