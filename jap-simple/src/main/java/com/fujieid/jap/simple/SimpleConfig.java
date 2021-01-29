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

import cn.hutool.crypto.digest.MD5;
import com.fujieid.jap.core.AuthenticateConfig;

import java.nio.charset.StandardCharsets;

import static com.fujieid.jap.core.JapConst.DEFAULT_CREDENTIAL_ENCRYPT_SALT;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
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
     * Credential encryption algorithm: MD5 encryption
     */
    private final MD5 credentialEncrypt;

    /**
     * Whether to enable remember me
     */
    private final boolean enableRememberMe;

    /**
     * Get the remember-me from request through {@code request.getParameter(`rememberMeField`)}, which defaults to "remember-me"
     */
    private String rememberMeField = "remember-me";

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
     * Generate the MD5 algorithm using the default key
     */
    public SimpleConfig() {
        // disabled
        this.enableRememberMe = false;
        this.credentialEncrypt = new MD5(DEFAULT_CREDENTIAL_ENCRYPT_SALT);
    }

    /**
     * Customize MD5 algorithms
     *
     * @param credentialEncrypt MD5 encryption
     */
    public SimpleConfig(MD5 credentialEncrypt) {
        this.enableRememberMe = true;
        this.credentialEncrypt = credentialEncrypt;
    }

    /**
     * Generate an MD5 encryption algorithm based on salt
     *
     * @param credentialEncryptSalt String salt
     */
    public SimpleConfig(String credentialEncryptSalt) {
        this.enableRememberMe = true;
        this.credentialEncrypt = new MD5(credentialEncryptSalt.getBytes(StandardCharsets.UTF_8));
    }

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

    public boolean isEnableRememberMe() {
        return enableRememberMe;
    }

    public MD5 getCredentialEncrypt() {
        return credentialEncrypt;
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
}
