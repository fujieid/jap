package com.fujieid.jap.core.cache;

import java.io.Serializable;

/**
 * JAP cache
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JapCache {

    /**
     * Set cache
     *
     * @param key   Cache key
     * @param value Cache value after serialization
     */
    void set(String key, Serializable value);

    /**
     * Set the cache and specify the expiration time of the cache
     *
     * @param key     Cache key
     * @param value   Cache value after serialization
     * @param timeout The expiration time of the cache, in milliseconds
     */
    void set(String key, Serializable value, long timeout);

    /**
     * Get cache value
     *
     * @param key Cache key
     * @return Cache value
     */
    Serializable get(String key);

    /**
     * Determine whether a key exists in the cache
     *
     * @param key Cache key
     * @return boolean
     */
    boolean containsKey(String key);
}
