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

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.ldap.exception.LdapPasswordException;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapPasswordMatchFactory {
    private static final Log log = LogFactory.get();

    public static LdapPasswordMatch getMatcherByPassword(String inputPassword) {
        log.debug("inputPassword: " + inputPassword);
        return getMatcher(LdapPasswordUtil.getAlgorithm(inputPassword));
    }

    public static LdapPasswordMatch getMatcher(String algorithm) {
        try {
            return getMatcher(PasswordAlgorithm.valueOf(algorithm));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new LdapPasswordException(JapErrorCode.LDAP_ILLEGAL_PASSWORD_ENCRYPTION_ALGORITHM);
        }
    }

    public static LdapPasswordMatch getMatcher(PasswordAlgorithm passwordAlgorithm) {
        log.debug("passwordAlgorithm: " + passwordAlgorithm);
        if (null == passwordAlgorithm) {
            throw new LdapPasswordException(JapErrorCode.LDAP_ILLEGAL_PASSWORD_ENCRYPTION_ALGORITHM);
        }
        LdapPasswordMatch ldapPasswordMatch = null;
        switch (passwordAlgorithm) {
            case CLEAR:
                ldapPasswordMatch = new LdapClearPasswordMatcher();
                break;
            case K5KEY:
                ldapPasswordMatch = new LdapK5keyPasswordMatcher();
                break;
            case MD5:
                ldapPasswordMatch = new LdapMd5PasswordMatcher();
                break;
            case SMD5:
                ldapPasswordMatch = new LdapSmd5PasswordMatcher();
                break;
            case SHA:
                ldapPasswordMatch = new LdapShaPasswordMatcher();
                break;
            case SSHA:
                ldapPasswordMatch = new LdapSshaPasswordMatcher();
                break;
            case SHA512:
                ldapPasswordMatch = new LdapSha512PasswordMatcher();
                break;
            case EXT_DES:
                ldapPasswordMatch = new LdapExtDespasswordMatcher();
                break;
            case MD5CRYPT:
                ldapPasswordMatch = new LdapMd5CryptPasswordMatcher();
                break;
            case SHA256CRYPT:
                ldapPasswordMatch = new LdapSha256CryptPasswordMatcher();
                break;
            case SHA512CRYPT:
                ldapPasswordMatch = new LdapSha512CryptPasswordMatcher();
                break;
            case CRYPT:
                ldapPasswordMatch = new LdapCryptPasswordMatcher();
                break;
            default:
                throw new LdapPasswordException(JapErrorCode.LDAP_ILLEGAL_PASSWORD_ENCRYPTION_ALGORITHM);
        }
        return ldapPasswordMatch;
    }
}
