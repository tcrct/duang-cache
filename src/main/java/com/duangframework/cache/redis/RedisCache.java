package com.duangframework.cache.redis;

import com.duangframework.cache.core.CacheModel;
import com.duangframework.cache.core.ICache;

public class RedisCache implements ICache<RedisCache> {

    private static Redis redis;
    private static final RedisCache REDIS_CACHE = new RedisCache();
    private CacheModel cacheModel;

    public static RedisCache duang() {
        if (null == redis) {
            redis = Redis.getInstance();
        }
        return REDIS_CACHE;
    }

    @Override
    public RedisCache model(CacheModel model) {
        cacheModel = model;
        return this;
    }

    public boolean set() {
        return redis.set(cacheModel);
    }

    @Override
    public <T> T get() {
        return redis.get(cacheModel);
    }

    @Override
    public long remove() {
        return redis.del(cacheModel);
    }
}
