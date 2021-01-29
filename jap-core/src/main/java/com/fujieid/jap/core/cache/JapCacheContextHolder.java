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

/**
 * Stores {@link com.fujieid.jap.core.cache.JapCache} for global calls
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapCacheContextHolder {

    private static JapCache currentJapCache;

    public static void enable(JapCache japCache) {
        currentJapCache = null == japCache ? new JapLocalCache() : japCache;
    }

    public static JapCache getCache() {
        if (null == currentJapCache) {
            JapCache japCache = new JapLocalCache();
            enable(japCache);
            return japCache;
        }
        return currentJapCache;
    }
}
