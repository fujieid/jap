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
package com.fujieid.jap.oauth2.pkce;

/**
 * Encryption method of pkce challenge code
 * <p>
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/14 11:56
 * @see <a href="https://docs.fujieid.com/college/protocol/oauth-2.0/oauth-2.0-pkce" target="_blank">https://docs.fujieid.com/college/protocol/oauth-2.0/oauth-2.0-pkce</a>
 * @see <a href="https://tools.ietf.org/html/rfc7636" target="_blank">https://tools.ietf.org/html/rfc7636</a>
 * @since 1.0.0
 */
public enum PkceCodeChallengeMethod {

    /**
     * code_challenge = code_verifier
     */
    PLAIN,
    /**
     * code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
     */
    S256
}
