package com.fujieid.jap.core.cache;

import java.util.concurrent.TimeUnit;

/**
 * Configuration of {@link com.fujieid.jap.core.cache.JapCache}
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapCacheConfig {

    /**
     * The cache expiration time is 1 day by default
     */
    public static long timeout = TimeUnit.DAYS.toMillis(1);
}
