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
package com.fujieid.jap.sso;

import com.baomidou.kisso.security.token.SSOToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapSsoUtil {

    public static SSOToken createSsoToken(Object userId, String username, HttpServletRequest request) {
        return new SSOToken()
            .setId(userId)
            .setIssuer(username)
            .setIp(request)
            .setUserAgent(request)
            .setTime(System.currentTimeMillis());
    }

    public static String createToken(Object userId, String username, HttpServletRequest request) {
        return createSsoToken(userId, username, request).getToken();
    }

    public static SSOToken parseToken(String token) {
        try {
            return SSOToken.parser(token);
        } catch (Exception e) {
            return null;
        }
    }
}
