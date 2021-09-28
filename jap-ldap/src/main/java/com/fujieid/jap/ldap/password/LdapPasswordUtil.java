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
package com.fujieid.jap.ldap.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapPasswordUtil {
    private static final Pattern PATTERN = Pattern.compile("\\{([a-zA-Z0-9]+)\\}");
    private static final Pattern SHA512CRYPT_PATTERN = Pattern.compile("\\{CRYPT\\}\\$6\\$([0-9A-Za-z]+)\\$");
    private static final Pattern SHA256CRYPT_PATTERN = Pattern.compile("\\{CRYPT\\}\\$5\\$([0-9A-Za-z]+)\\$");
    private static final Pattern MD5CRYPT_PATTERN = Pattern.compile("\\{CRYPT\\}\\$1\\$([0-9A-Za-z]+)\\$");
    private static final String CRYPT = "{CRYPT}";

    /**
     * Get the encryption algorithm from the password
     *
     * @param password Password in LDAP
     * @return PasswordAlgorithm
     */
    public static PasswordAlgorithm getAlgorithm(String password) {
        if (null == password || password.trim().length() == 0) {
            return null;
        }
        if (!password.startsWith("{")) {
            return PasswordAlgorithm.CLEAR;
        }

        if (password.startsWith(CRYPT)) {
            return getCryptPasswordAlgorithm(password);
        }
        Matcher matcher = PATTERN.matcher(password);
        if (matcher.find()) {
            try {
                return PasswordAlgorithm.valueOf(matcher.group(1));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return PasswordAlgorithm.CLEAR;
    }

    /**
     * Get the type of specific CRYPT encryption algorithm
     *
     * @param password Password in LDAP
     * @return PasswordAlgorithm
     */
    private static PasswordAlgorithm getCryptPasswordAlgorithm(String password) {
        // CRYPT_SHA512-The SHA-512 algorithm uses a 16-character string salt starting with $6$ for hashing.
        if (password.startsWith(CRYPT + "$6$")) {
            return PasswordAlgorithm.SHA512CRYPT;
        }
        // CRYPT_SHA256-The SHA-256 algorithm uses a 16-character string salt starting with $5$ for hashing.
        if (password.startsWith(CRYPT + "$5$")) {
            return PasswordAlgorithm.SHA256CRYPT;
        }
        // CRYPT_MD5-MD5 hash uses a 12-character string salt value starting with $1$.
        if (password.startsWith(CRYPT + "$1$")) {
            return PasswordAlgorithm.MD5CRYPT;
        }
        if (password.startsWith(CRYPT + "_")) {
            return PasswordAlgorithm.EXT_DES;
        }
        return PasswordAlgorithm.CRYPT;
    }

    /**
     * Obtain the salt value of the password encrypted using the Crypt algorithm
     *
     * @param originalPassword Password in LDAP
     * @param cryptType        Algorithm type: 1: md5crypt, 5: sha256crypt, 6: sha512crypt
     * @return salt value
     */
    public static String getCryptSaltByPassword(String originalPassword, int cryptType) {
        Pattern pattern = null;
        if (cryptType == 1) {
            pattern = MD5CRYPT_PATTERN;
        } else if (cryptType == 5) {
            pattern = SHA256CRYPT_PATTERN;
        } else {
            pattern = SHA512CRYPT_PATTERN;
        }
        Matcher matcher = pattern.matcher(originalPassword);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
