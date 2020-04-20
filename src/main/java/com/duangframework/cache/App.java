package com.duangframework.cache;

import com.duangframework.cache.core.CacheKeyModel;
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
        CacheKeyModel cacheKeyModel = create(CacheKeyEnum.USER.ID, "123");
        CacheKit.jvm().key("a").value("b").set();
        CacheKit.jvm().model(cacheKeyModel).set();
        CacheKit.redis().model(cacheKeyModel).value("laotang").set();


    }

    public static CacheKeyModel create(ICacheKeyEnums keyEnum, String key) {
        return new CacheKeyModel.Builder(keyEnum).customKey(key).build();
    }
}
