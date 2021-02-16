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
package com.fujieid.jap.simple;

import com.fujieid.jap.core.AuthenticateConfig;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @author harrylee (harryleexyz(a)qq.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleConfig extends AuthenticateConfig {

    /**
     * Get the user name from request through {@code request.getParameter(`usernameField`)}, which defaults to "username"
     */
    private String usernameField = "username";
    /**
     * Get the password from request through {@code request.getParameter(`passwordField`)}, which defaults to "password"
     */
    private String passwordField = "password";
    /**
     * Get the remember-me from request through {@code request.getParameter(`rememberMeField`)}, which defaults to "rememberMe"
     */
    private String rememberMeField = "rememberMe";

    /**
     * Default remember me cookie key
     */
    private String rememberMeCookieKey = "_jap_remember_me";

    /**
     * Remember me cookie expire, unit: second, default 60*60*24*30[month]
     */
    private Integer rememberMeCookieExpire = 2592000;

    /**
     * Remember me cookie domain
     */
    private String rememberMeCookieDomain;

    /**
     * Credential Encrypt Salt
     */
    private String credentialEncryptSalt = "_jap:rememberMe";

    public String getUsernameField() {
        return usernameField;
    }

    public SimpleConfig setUsernameField(String usernameField) {
        this.usernameField = usernameField;
        return this;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public SimpleConfig setPasswordField(String passwordField) {
        this.passwordField = passwordField;
        return this;
    }

    public String getRememberMeField() {
        return rememberMeField;
    }

    public SimpleConfig setRememberMeField(String rememberMeField) {
        this.rememberMeField = rememberMeField;
        return this;
    }

    public String getRememberMeCookieKey() {
        return rememberMeCookieKey;
    }

    public SimpleConfig setRememberMeCookieKey(String rememberMeCookieKey) {
        this.rememberMeCookieKey = rememberMeCookieKey;
        return this;
    }

    public Integer getRememberMeCookieExpire() {
        return rememberMeCookieExpire;
    }

    public SimpleConfig setRememberMeCookieExpire(Integer rememberMeCookieExpire) {
        this.rememberMeCookieExpire = rememberMeCookieExpire;
        return this;
    }

    public String getRememberMeCookieDomain() {
        return rememberMeCookieDomain;
    }

    public SimpleConfig setRememberMeCookieDomain(String rememberMeCookieDomain) {
        this.rememberMeCookieDomain = rememberMeCookieDomain;
        return this;
    }

    public String getCredentialEncryptSalt() {
        return credentialEncryptSalt;
    }

    public SimpleConfig setCredentialEncryptSalt(String credentialEncryptSalt) {
        this.credentialEncryptSalt = credentialEncryptSalt;
        return this;
    }
}
