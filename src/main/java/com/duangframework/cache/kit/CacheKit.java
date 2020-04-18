package com.duangframework.cache.kit;

import com.duangframework.cache.ehcache.EhCache;
import com.duangframework.cache.jvm.JvmCache;
import com.duangframework.cache.redis.RedisCache;

/**
 * 缓存工具类
 *
 * @author Laotang
 * @since 1.0
 */
public class CacheKit {

    public static JvmCache  JVM_CACHE;
    public static RedisCache REDIS_CACHE;
    public static EhCache  EHCACHE_CACHE;

    // jvm Map
    public static JvmCache jvm() {
        if (null == JVM_CACHE) {
            JVM_CACHE = JvmCache.duang();
        }
        return JVM_CACHE;
    }

    // ehcache
    public static EhCache ehcache() {
        if (null == EHCACHE_CACHE) {
            EHCACHE_CACHE = EhCache.duang();
        }
        return EHCACHE_CACHE;
    }

    // redis
    public static RedisCache redis() {
        if (null == REDIS_CACHE) {
            REDIS_CACHE = RedisCache.duang();
        }
        return REDIS_CACHE;
    }

}
