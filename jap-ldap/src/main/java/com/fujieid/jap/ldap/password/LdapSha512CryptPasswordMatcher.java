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

import org.apache.commons.codec.digest.Crypt;

/**
 * sha512crypt:   {CRYPT}$6$nNLKOfFc$XA9TM0MJtSrCCkZYTkaKlRYZ/mvLehnZ9ovX0WHaqPgMIuiMQxcgUKpp6ZVM2Kuqqk1e2UZUsKX4VJ0YAPmgx1
 * <p>
 * https://www.thinbug.com/q/31401353
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapSha512CryptPasswordMatcher implements LdapPasswordMatch {
    protected int cryptType;

    public LdapSha512CryptPasswordMatcher() {
        this.cryptType = 6;
    }

    @Override
    public boolean matches(String inputPassword, String originalPassword) {
        if (originalPassword == null || inputPassword == null) {
            return false;
        }
        if (originalPassword.equals(inputPassword)) {
            return true;
        }

        String salt = LdapPasswordUtil.getCryptSaltByPassword(originalPassword, this.cryptType);
        String encodedHash = Crypt.crypt(inputPassword, "$" + this.cryptType + "$" + salt);
        String newPass = String.format("{CRYPT}%s", encodedHash);
        return newPass.equals(originalPassword);
    }
}
