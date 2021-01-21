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
package com.fujieid.jap.sso.config;

/**
 * SSO Config
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 12:17
 * @since 1.0.0
 */
public class JapSsoConfig {

    /**
     * cookie name, default is `_jap_sso_id`
     */
    private String cookieName = "_jap_sso_id";
    /**
     * The domain name of the cookie. By default, it is the current access domain name.
     */
    private String cookieDomain;
    /**
     * The validity of the cookie
     */
    private int cookieMaxAge = Integer.MAX_VALUE;
    /**
     * Parameter name of callback url after successful login
     */
    private String paramReturnUrl = "returnUrl";

    /**
     * Login Url. Default is `/login`
     */
    @Deprecated
    private String loginUrl = "/login";

    /**
     * Logout Url. Default is `/logout`
     */
    @Deprecated
    private String logoutUrl = "/logout";

    public String getCookieName() {
        return cookieName;
    }

    public JapSsoConfig setCookieName(String cookieName) {
        this.cookieName = cookieName;
        return this;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public JapSsoConfig setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
        return this;
    }

    public String getParamReturnUrl() {
        return paramReturnUrl;
    }

    public JapSsoConfig setParamReturnUrl(String paramReturnUrl) {
        this.paramReturnUrl = paramReturnUrl;
        return this;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public JapSsoConfig setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public JapSsoConfig setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
        return this;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public JapSsoConfig setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
        return this;
    }
}
