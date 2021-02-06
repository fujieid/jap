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
