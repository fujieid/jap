package com.fujieid.jap.core.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

import java.io.Serializable;

/**
 * Default cache implementation
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapLocalCache implements JapCache {

    private static final TimedCache<String, Serializable> LOCAL_CACHE = CacheUtil.newTimedCache(JapCacheConfig.timeout);

    /**
     * Set cache
     *
     * @param key   Cache key
     * @param value Cache value after serialization
     */
    @Override
    public void set(String key, Serializable value) {
        set(key, value, JapCacheConfig.timeout);
    }

    /**
     * Set the cache and specify the expiration time of the cache
     *
     * @param key     Cache key
     * @param value   Cache value after serialization
     * @param timeout expiration time of the cache
     */
    @Override
    public void set(String key, Serializable value, long timeout) {
        LOCAL_CACHE.put(key, value, timeout);
    }

    /**
     * Get cache value
     *
     * @param key Cache key
     * @return Cache value
     */
    @Override
    public Serializable get(String key) {
        return LOCAL_CACHE.get(key);
    }

    /**
     * Determine whether a key exists in the cache
     *
     * @param key Cache key
     * @return boolean
     */
    @Override
    public boolean containsKey(String key) {
        return LOCAL_CACHE.containsKey(key);
    }
}
