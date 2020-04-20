package com.duangframework.cache.redis;

import com.duangframework.cache.core.CacheKeyModel;
import com.duangframework.cache.core.ICache;

public class RedisCache implements ICache<RedisCache> {

    private static Redis redis;
    private static final RedisCache REDIS_CACHE = new RedisCache();
    private CacheKeyModel cacheKeyModel;
    private Object value;

    public static RedisCache duang() {
        if (null == redis) {
            redis = Redis.getInstance();
        }
        return REDIS_CACHE;
    }

    @Override
    public RedisCache model(CacheKeyModel model) {
        cacheKeyModel = model;
        return this;
    }

    public RedisCache value(Object value) {
        this.value = value;
        return this;
    }

    public boolean set() {
        return redis.set(cacheKeyModel, value);
    }

    @Override
    public <T> T get() {
        return redis.get(cacheKeyModel);
    }

    @Override
    public long remove() {
        return redis.del(cacheKeyModel);
    }

}
