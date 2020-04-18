package com.duangframework.cache.ehcache;

import com.duangframework.cache.core.ICache;

public class EhCache implements ICache {

    public static final EhCache EH_CACHE = new EhCache();

    public static EhCache duang() {
        return EH_CACHE;
    }

    public void expire(long expireTime) {

    }

    @Override
    public <T> T get() {
        return null;
    }

    @Override
    public boolean remove() {
        return false;
    }
}
