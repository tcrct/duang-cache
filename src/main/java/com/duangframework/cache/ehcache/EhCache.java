package com.duangframework.cache.ehcache;

import com.duangframework.cache.core.CacheModel;
import com.duangframework.cache.core.ICache;

public class EhCache implements ICache<EhCache> {

    public static final EhCache EH_CACHE = new EhCache();

    public static EhCache duang() {
        return EH_CACHE;
    }

    public void expire(long expireTime) {

    }

    @Override
    public EhCache model(CacheModel model) {
        return null;
    }

    @Override
    public <T> T get() {
        return null;
    }

    @Override
    public long remove() {
        return 1;
    }
}
