package com.duangframework.cache.ehcache;

import com.duangframework.cache.core.CacheKeyModel;
import com.duangframework.cache.core.ICache;
import com.duangframework.cache.kit.CacheKit;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCache implements ICache<EhCache> {

    private static final Logger LOG = LoggerFactory.getLogger(EhCache.class);

    public static EhCache EH_CACHE;
    private static CacheManager cacheManager;
    private Cache ehCache;

    public EhCache(String cacheName, Configuration configuration) {
        if(null == ehCache) {
            if (configuration != null) {
                cacheManager = CacheManager.create(configuration);
                return ;
            }
            cacheManager = net.sf.ehcache.CacheManager.create();

            ehCache = cacheManager.getCache(cacheName);
            if (ehCache == null) {
                synchronized(CacheKit.class) {
                    ehCache = cacheManager.getCache(cacheName);
                    if (ehCache == null) {
                        LOG.warn("Could not find cache config [" + cacheName + "], using default.");
                        cacheManager.addCacheIfAbsent(cacheName);
                        ehCache = cacheManager.getCache(cacheName);
                        LOG.debug("Cache [" + cacheName + "] started.");
                    }
                }
            }
        }
        EH_CACHE = this;
    }

    public static EhCache duang() {
        return EH_CACHE;
    }


    private String key;
    private Object value;

    public EhCache key(String key) {
        this.key = key;
        return this;
    }

    public EhCache value(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public EhCache model(CacheKeyModel model) {
        return null;
    }

    @Override
    public <T> T get() {
        Element element = ehCache.get(key);
        return element != null ? (T)element.getObjectValue() : null;
    }

    @Override
    public long remove() {
        ehCache.remove(key);
        return 1;
    }

    public void close() throws Exception {
        cacheManager.clearAll();
        cacheManager.shutdown();
    }


    public void set() {
        ehCache.put(new Element(key, value));
    }

    public void putIfAbsent() {
        ehCache.putIfAbsent(new Element(key, value));
    }

    public void replace() {
        ehCache.replace(new Element(key, value));
    }
}
