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
package com.fujieid.jap.core.config;

import com.fujieid.jap.sso.config.JapSsoConfig;

import java.util.concurrent.TimeUnit;

/**
 * Jap configuration.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapConfig {

    /**
     * Enable sso, is not enabled by default
     */
    private boolean sso;

    /**
     * SSO config
     */
    private JapSsoConfig ssoConfig;

    /**
     * After the user logs in successfully, the valid time of the token, in milliseconds, the default validity period is 7 days
     */
    private long tokenExpireTime = TimeUnit.DAYS.toMillis(7);

    /**
     * The expiration time of the jap cache, in milliseconds, the default validity period is 7 days
     */
    private long cacheExpireTime = TimeUnit.DAYS.toMillis(7);

    public boolean isSso() {
        return sso;
    }

    public JapConfig setSso(boolean sso) {
        this.sso = sso;
        return this;
    }

    public JapSsoConfig getSsoConfig() {
        return ssoConfig;
    }

    public JapConfig setSsoConfig(JapSsoConfig ssoConfig) {
        this.ssoConfig = ssoConfig;
        return this;
    }

    public long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public JapConfig setTokenExpireTime(long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
        return this;
    }

    public long getCacheExpireTime() {
        return cacheExpireTime;
    }

    public JapConfig setCacheExpireTime(long cacheExpireTime) {
        this.cacheExpireTime = cacheExpireTime;
        return this;
    }
}
