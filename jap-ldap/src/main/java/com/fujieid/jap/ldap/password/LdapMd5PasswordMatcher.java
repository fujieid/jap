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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * {MD5}4QrcOUm6Wau+VuBX8g+IPg==
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapMd5PasswordMatcher implements LdapPasswordMatch {

    @Override
    public String encode(String inputPassword) {
        byte[] byteArray = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(inputPassword.getBytes(StandardCharsets.UTF_8));
            byteArray = md.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{MD5}" + Base64.encode(byteArray);
    }
}
