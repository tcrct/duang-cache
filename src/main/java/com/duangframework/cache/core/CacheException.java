package com.duangframework.cache.core;

public class CacheException extends RuntimeException {

    public CacheException(String errMessage) {
           super(errMessage);
    }

    public CacheException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
