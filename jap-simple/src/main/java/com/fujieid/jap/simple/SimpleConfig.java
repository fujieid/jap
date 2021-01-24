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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.fujieid.jap.core.AuthenticateConfig;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 18:02
 * @since 1.0.0
 */
public class SimpleConfig extends AuthenticateConfig {

    /**
     * Credential encryption algorithm: MD5 encryption
     */
    private final MD5 credentialEncrypt;

    /**
     * Default salt. Default salt is not recommended
     */
    private static final byte[] DEFAULT_CREDENTIAL_ENCRYPT_SALT = "jap:123456".getBytes(StandardCharsets.UTF_8);

    /**
     * default delimiter
     */
    private static final char DEFAULT_DELIMITER = ':';

    /**
     * Whether to enable remember me
     */
    private final boolean enableRememberMe;

    /**
     * Get the user name from request through {@code request.getParameter(`usernameField`)}, which defaults to "username"
     */
    private String usernameField = "username";
    /**
     * Get the password from request through {@code request.getParameter(`passwordField`)}, which defaults to "password"
     */
    private String passwordField = "password";

    /**
     * Get the remember-me from request through {@code request.getParameter(`rememberMeField`)}, which defaults to "remember-me"
     */
    private String rememberMeField = "remember-me";

    /**
     * By default, remember me cookie key
     */
    private String rememberMeCookieKey = "_jap_remember_me";

    /**
     * remember me cookie expire, unit: second, default 60*60*24*30[month]
     */
    private Integer rememberMeCookieExpire = 2592000;

    /**
     * remember me cookie domain
     */
    private String rememberMeCookieDomain;

    /**
     * Generate the MD5 algorithm using the default key
     */
    public SimpleConfig() {
        this.enableRememberMe = false;
        this.credentialEncrypt = new MD5(DEFAULT_CREDENTIAL_ENCRYPT_SALT);
    }

    /**
     * Whether enable RememberMe
     *
     * @return
     */
    public boolean isEnableRememberMe() {
        return enableRememberMe;
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

    public String getRememberMeField() {
        return rememberMeField;
    }

    public void setRememberMeField(String rememberMeField) {
        this.rememberMeField = rememberMeField;
    }

    public String getRememberMeCookieKey() {
        return rememberMeCookieKey;
    }

    public void setRememberMeCookieKey(String rememberMeCookieKey) {
        this.rememberMeCookieKey = rememberMeCookieKey;
    }

    public Integer getRememberMeCookieExpire() {
        return rememberMeCookieExpire;
    }

    public void setRememberMeCookieExpire(Integer rememberMeCookieExpire) {
        this.rememberMeCookieExpire = rememberMeCookieExpire;
    }

    public String getRememberMeCookieDomain() {
        return rememberMeCookieDomain;
    }

    public void setRememberMeCookieDomain(String rememberMeCookieDomain) {
        this.rememberMeCookieDomain = rememberMeCookieDomain;
    }

    /**
     * encode
     *
     * @param username username
     * @param password password [keep]
     * @return
     */
    public RememberMeDetails encode(String username, String password) {
        return RememberMeDetails.encodeInstance(this, username);
    }

    /**
     * decode
     *
     * @param cookieValue
     * @return
     */
    public RememberMeDetails decode(String cookieValue) {
        return RememberMeDetails.decodeInstance(this, cookieValue);
    }

    /**
     * @author harrylee (harryleexyz(a)qq.com)
     * @version 1.0.0
     * @date 2021/1/24 14:57
     * @since 1.0.0
     */
    public static class RememberMeDetails {

        /**
         * username
         */
        private String username;

        /**
         * expiry time
         */
        private long expiryTime;
        /**
         * encode value
         */
        private String encodeValue;

        public String getUsername() {
            return username;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        public String getEncodeValue() {
            return encodeValue;
        }

        private SimpleConfig simpleConfig;

        private RememberMeDetails() {
        }

        /**
         * Encrypted acquisition instance.
         *
         * @param simpleConfig config
         * @param username     username
         * @return
         */
        public static RememberMeDetails encodeInstance(SimpleConfig simpleConfig, String username) {
            RememberMeDetails rememberMeDetails = new RememberMeDetails();
            rememberMeDetails.username = username;
            long expiryTime = System.currentTimeMillis() + simpleConfig.getRememberMeCookieExpire();
            rememberMeDetails.expiryTime = expiryTime;
            // username:tokenExpiryTime
            String md5Data = username + DEFAULT_DELIMITER + expiryTime;
            String md5Key = simpleConfig.credentialEncrypt.digestHex16(md5Data);
            // username:tokenExpiryTime:key
            String base64Data = md5Data + DEFAULT_DELIMITER + md5Key;
            rememberMeDetails.encodeValue = new String(Base64.getEncoder().encode(base64Data.getBytes(StandardCharsets.UTF_8)));
            return rememberMeDetails;
        }

        /**
         * Decryption acquisition instance.
         *
         * @param simpleConfig config
         * @param cookieValue  cookie value
         * @return
         */
        public static RememberMeDetails decodeInstance(SimpleConfig simpleConfig, String cookieValue) {
            if (!simpleConfig.enableRememberMe) {
                return null;
            }
            RememberMeDetails rememberMeDetails = new RememberMeDetails();
            rememberMeDetails.encodeValue = cookieValue;
            String base64DecodeValue;
            try {
                base64DecodeValue = new String(Base64.getDecoder().decode(cookieValue.getBytes(StandardCharsets.UTF_8)));
            } catch (RuntimeException e) {
                return null;
            }
            String[] base64DecodeValueSplitArray = StrUtil.splitToArray(base64DecodeValue, DEFAULT_DELIMITER);
            // Check and validate keys
            if (base64DecodeValueSplitArray.length > 2) {
                String username = base64DecodeValueSplitArray[0];
                long expiryTime;
                try {
                    expiryTime = Long.parseLong(base64DecodeValueSplitArray[1]);
                } catch (RuntimeException e) {
                    return null;
                }
                // overdue
                if (expiryTime < System.currentTimeMillis()) {
                    return null;
                }
                // username:tokenExpiryTime
                String md5Data = username + DEFAULT_DELIMITER + expiryTime;
                String md5Key = simpleConfig.credentialEncrypt.digestHex16(md5Data);
                // Check pass returns
                if (ObjectUtil.equal(md5Key, base64DecodeValueSplitArray[2])) {
                    rememberMeDetails.username = username;
                    rememberMeDetails.expiryTime = expiryTime;
                    return rememberMeDetails;
                }
            }
            return null;
        }
    }

}
