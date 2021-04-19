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
package com.fujieid.jap.core.context;

import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.store.JapUserStore;

/**
 * The context of jap.
 * <p>
 * Persist jap user store, jap cache and jap config in memory to facilitate the management of jap user data.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapContext {
    /**
     * user store
     */
    private JapUserStore userStore;
    /**
     * jap cache
     */
    private JapCache cache;
    /**
     * Jap configuration.
     */
    private JapConfig config;

    public JapContext() {
    }

    public JapContext(JapUserStore userStore, JapCache cache, JapConfig config) {
        this.userStore = userStore;
        this.cache = cache;
        this.config = config;
    }

    public JapUserStore getUserStore() {
        return userStore;
    }

    public JapContext setUserStore(JapUserStore userStore) {
        this.userStore = userStore;
        return this;
    }

    public JapCache getCache() {
        return cache;
    }

    public JapContext setCache(JapCache cache) {
        this.cache = cache;
        return this;
    }

    public JapConfig getConfig() {
        return config;
    }

    public JapContext setConfig(JapConfig config) {
        this.config = config;
        return this;
    }
}
