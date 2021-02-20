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
package com.fujieid.jap.core;

import com.fujieid.jap.sso.config.JapSsoConfig;

/**
 * Jap configuration.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapConfig {

    /**
     * Login Url. Default is `/login`
     */
    private String loginUrl = "/login";

    /**
     * Logout Url. Default is `/logout`
     */
    private String logoutUrl = "/logout";

    /**
     * Enable sso, is not enabled by default
     */
    private boolean sso;

    /**
     * SSO config
     */
    private JapSsoConfig ssoConfig;

    /**
     * After successful login, redirect to {@code successRedirect}. Default is `/`
     */
    private String successRedirect = "/";

    /**
     * After logout, redirect to {@code logoutRedirect}. Default is `/`
     */
    private String logoutRedirect = "/";

    /**
     * After failed login, redirect to {@code failureRedirect}. Default is `/error`
     */
    private String failureRedirect = "/error";

    public String getLoginUrl() {
        return loginUrl;
    }

    public JapConfig setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public JapConfig setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
        return this;
    }

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

    public String getSuccessRedirect() {
        return successRedirect;
    }

    public JapConfig setSuccessRedirect(String successRedirect) {
        this.successRedirect = successRedirect;
        return this;
    }

    public String getFailureRedirect() {
        return failureRedirect;
    }

    public JapConfig setFailureRedirect(String failureRedirect) {
        this.failureRedirect = failureRedirect;
        return this;
    }

    public String getLogoutRedirect() {
        return logoutRedirect;
    }

    public JapConfig setLogoutRedirect(String logoutRedirect) {
        this.logoutRedirect = logoutRedirect;
        return this;
    }
}
