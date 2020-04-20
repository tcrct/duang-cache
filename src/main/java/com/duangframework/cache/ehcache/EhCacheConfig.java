package com.duangframework.cache.ehcache;

import net.sf.ehcache.config.Configuration;

public class EhCacheConfig {

    private String clientName = null;
    private Configuration config = null;

    private EhCacheConfig(String clientName, Configuration config) {
        this.clientName = clientName;
        this.config = config;
    }

    public static class Builder {
        private String clientName = null;
        private Configuration config = null;

        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }
        public Builder config(Configuration config) {
            this.config = config;
            return this;
        }

        public EhCacheConfig build() {
            return new EhCacheConfig(clientName, config);
        }
    }

    public String getClientName() {
        return clientName;
    }

    public Configuration getConfig() {
        return config;
    }
}
