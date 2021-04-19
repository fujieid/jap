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

import com.fujieid.jap.ids.model.AuthCode;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.UserInfo;

/**
 * oauth 2.0 related methods
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Oauth2Service {

    /**
     * Generate authorization code
     *
     * @param param         Parameters requested by the client
     * @param user          User Info
     * @param codeExpiresIn code expiration time
     * @return String
     */
    String createAuthorizationCode(IdsRequestParam param, UserInfo user, Long codeExpiresIn);

    /**
     * Verification authorization code
     *
     * @param grantType grant Type
     * @param code      authorization code
     * @return AuthCode
     */
    AuthCode validateAndGetAuthrizationCode(String grantType, String code);

    /**
     * When the pkce protocol is enabled, the code challenge needs to be verified
     *
     * @param codeVerifier code verifier
     * @param code         authorization code
     * @see <a href="https://tools.ietf.org/html/rfc7636">https://tools.ietf.org/html/rfc7636</a>
     */
    void validateAuthrizationCodeChallenge(String codeVerifier, String code);

    /**
     * Obtain auth code info by authorization code
     *
     * @param code authorization code
     * @return string
     */
    AuthCode getCodeInfo(String code);

    /**
     * Delete authorization code
     *
     * @param code authorization code
     */
    void invalidateCode(String code);

}
