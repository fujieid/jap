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

import cn.hutool.core.codec.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * {SHA}fEqNCco3Yq9h5ZUglD3CZJT4lBs=
 * <p>
 * http://gurolerdogan.blogspot.com/2010/03/ssha-encryption-with-java.html
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapShaPasswordMatcher implements LdapPasswordMatch {

    @Override
    public boolean matches(String inputPassword, String originalPassword) {
        if (originalPassword == null || inputPassword == null) {
            return false;
        }
        if (originalPassword.equals(inputPassword)) {
            return true;
        }
        PasswordAlgorithm algorithm = LdapPasswordUtil.getAlgorithm(originalPassword);

        // parse out encrypted characters
        originalPassword = originalPassword.substring(algorithm.name().length() + 2);

        int saltSize = 20;
        if (algorithm == PasswordAlgorithm.SHA256) {
            saltSize = 32;
        } else if (algorithm == PasswordAlgorithm.SHA512) {
            saltSize = 64;
        }

        byte[] ldapPasswordByte = Base64.decode(originalPassword);
        byte[] shaCode;
        byte[] salt;

        // The first `saltSize` bit is the encrypted segment, and after the `saltSize` bit
        // is the random plaintext when initially encrypted
        if (ldapPasswordByte.length <= saltSize) {
            shaCode = ldapPasswordByte;
            salt = new byte[0];
        } else {
            shaCode = new byte[saltSize];
            salt = new byte[ldapPasswordByte.length - saltSize];
            System.arraycopy(ldapPasswordByte, 0, shaCode, 0, saltSize);
            System.arraycopy(ldapPasswordByte, saltSize, salt, 0, salt.length);
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm.getAlgorithm());
            md.update(inputPassword.getBytes());
            md.update(salt);
            byte[] inputPasswordByte = md.digest();
            return MessageDigest.isEqual(shaCode, inputPasswordByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
}
