package com.duangframework.cache.core;

/**
 * 缓存接口
 */
public interface ICache<E> {


    E model(CacheModel model);


    /**
     * 取缓存值
     * @return
     */
    <V> V get();


    /**
     *  删除缓存
     * @return
     */
    long remove();


}
