package com.duangframework.cache.core;

import com.duangframework.cache.kit.ToolsKit;

public class CacheKeyModel {

    private String keyPrefix;
    private String customKey;
    private Integer ttl;
    private String keyDesc;


    public static class Builder {

        private String customKey;
        private String keyPrefix;
        private int ttl;
        private String keyDesc;

        public Builder() { }
        /**
         * 枚举对象设置相关值
         * @param enums
         */
        public Builder(ICacheKeyEnums enums) {
            this.keyPrefix = enums.getKeyPrefix();
            this.ttl = enums.getKeyTTL();
            this.keyDesc = enums.getKeyDesc();
        }

        /**
         * 自定义前缀
         * @param keyPrefix key关键字的前缀
         * @return
         */
        public Builder keyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
            return this;
        }

        /**
         * 自定义的key值，一般用于区分，例如ID值
         * @param customKey 自定义的key值
         * @return
         */
        public Builder customKey(String customKey) {
            this.customKey = customKey;
            return this;
        }

        public CacheKeyModel build() {
            return new CacheKeyModel(this);
        }
    }


    private CacheKeyModel(Builder builder) {
        keyPrefix = builder.keyPrefix;
        customKey = builder.customKey;
        ttl = builder.ttl;
        keyDesc = builder.keyDesc;
    }

    /**
     * 将key前缀值与自定义的key值结合，组成最终key值
     * @return
     */
    public String getKey() {
        if(keyPrefix.endsWith(":") && ToolsKit.isNotEmpty(customKey)){
            return keyPrefix + customKey;
        } else {
            return ToolsKit.isNotEmpty(customKey) ?keyPrefix+":"+customKey : keyPrefix;
        }
    }

    /**
     * 缓存过期时间
     * @return
     */
    public Integer getKeyTTL() {
        if(ttl <= 0 ) {
            ttl = ICacheKeyEnums.NEVER_TTL;
        }
        return ttl;
    }

    /**
     * 缓存key说明
     * @return
     */
    public String getKeyDesc() {
        return keyDesc;
    }

}
