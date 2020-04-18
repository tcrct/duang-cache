package com.duangframework.cache.demo;

import com.duangframework.cache.core.ICacheKeyEnums;

public class CacheKeyEnum  {

    public enum USER implements ICacheKeyEnums {

        ID("duang:user", ICacheKeyEnums.DEFAULT_TTL, "User对象缓存Key前缀"),
        ;

        private final String prefix;
        private final int ttl;
        private final String desc;

        USER(String prefix , int ttl, String desc) {
            this.prefix = prefix;
            this.ttl = ttl;
            this.desc = desc;
        }

        public String getKeyPrefix() {
            return prefix;
        }

        public int getKeyTTL() {
            return ttl;
        }

        public String getKeyDesc() {
            return desc;
        }
    }

}
