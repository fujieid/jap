/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.core.cache;

import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Default cache implementation
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapLocalCache implements JapCache {

    private static final Map<String, CacheObj> LOCAL_CACHE = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock(true);
    private final Lock writeLock = cacheLock.writeLock();
    private final Lock readLock = cacheLock.readLock();

    public JapLocalCache() {
        if (JapCacheConfig.schedulePrune) {
            this.schedulePrune(JapCacheConfig.timeout);
        }
    }

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
        writeLock.lock();
        try {
            LOCAL_CACHE.put(key, new CacheObj(value, timeout));
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Get cache value
     *
     * @param key Cache key
     * @return Cache value
     */
    @Override
    public Serializable get(String key) {
        if (StrUtil.isEmpty(key)) {
            return null;
        }
        readLock.lock();
        try {
            CacheObj cacheObj = LOCAL_CACHE.get(key);
            if (null == cacheObj || cacheObj.isExpired()) {
                return null;
            }
            return cacheObj.getData();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Determine whether a key exists in the cache
     *
     * @param key Cache key
     * @return boolean
     */
    @Override
    public boolean containsKey(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        readLock.lock();
        try {
            CacheObj cacheObj = LOCAL_CACHE.get(key);
            return null != cacheObj && !cacheObj.isExpired();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Delete the key from the cache
     *
     * @param key Cache key
     */
    @Override
    public void removeKey(String key) {
        writeLock.lock();
        try {
            LOCAL_CACHE.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Start a scheduled task to clean up expired cache
     *
     * @param delay Interval duration, in milliseconds
     */
    public void schedulePrune(long delay) {
        AuthCacheScheduler.INSTANCE.schedule(this::pruneCache, delay);
    }

    /**
     * Clean up expired cache
     */
    public void pruneCache() {
        Iterator<CacheObj> values = LOCAL_CACHE.values().iterator();
        CacheObj cacheObj;
        while (values.hasNext()) {
            cacheObj = values.next();
            if (cacheObj.isExpired()) {
                values.remove();
            }
        }
    }

    /**
     * Cache scheduler
     */
    private enum AuthCacheScheduler {
        /**
         * AuthCacheScheduler
         */
        INSTANCE;

        private final AtomicInteger cacheTaskNumber = new AtomicInteger(1);
        private ScheduledExecutorService scheduler;

        AuthCacheScheduler() {
            create();
        }

        private void create() {
            this.shutdown();
            this.scheduler = new ScheduledThreadPoolExecutor(10, r -> new Thread(r, String.format("JustAuth-Task-%s", cacheTaskNumber.getAndIncrement())));
        }

        public void shutdown() {
            if (null != scheduler) {
                this.scheduler.shutdown();
            }
        }

        public void schedule(Runnable task, long delay) {
            this.scheduler.scheduleAtFixedRate(task, delay, delay, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Cache Object
     */
    private static class CacheObj implements Serializable {
        private final Serializable data;
        private final long expire;

        CacheObj(Serializable data, long expire) {
            this.data = data;
            // The actual expiration time is equal to the current time plus the validity period
            this.expire = System.currentTimeMillis() + expire;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > this.expire;
        }

        public Serializable getData() {
            return data;
        }

        public long getExpire() {
            return expire;
        }
    }
}
