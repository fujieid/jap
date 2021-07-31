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
package com.fujieid.jap.ids.service;

import cn.hutool.crypto.SecureUtil;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.util.JwtUtil;

import java.util.Set;
import java.util.TreeSet;

/**
 * 创建 Token（包含 access_token 和 refresh_token） 以及校验 access_token 的接口
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IdsTokenService {

    /**
     * Create an access token, Use jwt by default.
     * <p>
     * Developers can reimplement this method to generate access token in any format.
     *
     * @param clientId Client Identifier
     * @param userinfo User Profile
     *                 0-     * @param tokenExpireIn Id Token validity (seconds)
     * @param nonce    Random string
     * @param issuer   The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     */
    default String createAccessToken(String clientId, UserInfo userinfo, Long tokenExpireIn, String nonce, String issuer) {
        return this.createAccessToken(clientId, userinfo, tokenExpireIn, nonce, issuer, null);
    }

    /**
     * Create an access token, Use jwt by default.
     * <p>
     * Developers can reimplement this method to generate access token in any format.
     *
     * @param clientId Client Identifier
     * @param userinfo User Profile
     *                 0-     * @param tokenExpireIn Id Token validity (seconds)
     * @param nonce    Random string
     * @param issuer   The issuer name. This parameter cannot contain the colon (:) character.
     * @param scopes   The scope granted by the current access token
     * @return String
     */
    default String createAccessToken(String clientId, UserInfo userinfo, Long tokenExpireIn, String nonce, String issuer, Set<String> scopes) {
        return JwtUtil.createJwtToken(clientId, userinfo, tokenExpireIn, nonce, scopes, null, issuer);
    }

    /**
     * Create a refresh token，default is {@code sha256(client + scope + timestamp) }
     * <p>
     * Developers can reimplement this method to generate refresh token in any format.
     *
     * @param clientId Client Identifier
     * @param scopes   The scope granted by the current refresh token
     * @return String
     */
    default String createRefreshToken(String clientId, Set<String> scopes) {
        scopes = null == scopes || scopes.size() == 0 ? new TreeSet<>() : scopes;
        return SecureUtil.sha256(clientId.concat(String.join(",", scopes)).concat(System.currentTimeMillis() + ""));
    }

    /**
     * Check the availability of access token
     *
     * @param accessToken access_token
     * @return bool
     */
    boolean verifyAccessToken(String accessToken);
}
