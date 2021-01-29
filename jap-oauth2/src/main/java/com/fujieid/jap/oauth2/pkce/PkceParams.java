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
 * OAuth PKCE Parameters Registry
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://tools.ietf.org/html/rfc7636#section-6.1" target="_blank">6.1.  OAuth Parameters Registry</a>
 * @since 1.0.0
 */
public interface PkceParams {

    /**
     * {@code code_challenge} - used in Authorization Request.
     */
    String CODE_CHALLENGE = "code_challenge";

    /**
     * {@code code_challenge_method} - used in Authorization Request.
     */
    String CODE_CHALLENGE_METHOD = "code_challenge_method";

    /**
     * {@code code_verifier} - used in Token Request.
     */
    String CODE_VERIFIER = "code_verifier";

}
