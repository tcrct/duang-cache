package com.duangframework.cache;

import com.duangframework.cache.core.CacheModel;
import com.duangframework.cache.core.ICacheKeyEnums;
import com.duangframework.cache.demo.CacheKeyEnum;
import com.duangframework.cache.kit.CacheKit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CacheModel cacheModel = create(CacheKeyEnum.USER.ID, "123", "laotang");
        CacheKit.jvm().key("a").value("b").set();
        CacheKit.jvm().model(cacheModel).set();
        CacheKit.redis().model(cacheModel).set();

        CacheKit.ehcache().expire(123);

    }

    public static CacheModel create(ICacheKeyEnums keyEnum, String key, Object value) {
        return new CacheModel.Builder(keyEnum).customKey(key).value(value).builder();
    }
}
