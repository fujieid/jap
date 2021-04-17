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
package com.fujieid.jap.ids.model;

import java.io.Serializable;

/**
 * Authorization code
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.1
 */
public class AuthCode implements Serializable {
    private String scope;
    private UserInfo user;
    private String nonce;
    private String codeChallengeMethod;
    private String codeChallenge;

    public String getScope() {
        return scope;
    }

    public AuthCode setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public UserInfo getUser() {
        return user;
    }

    public AuthCode setUser(UserInfo user) {
        this.user = user;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public AuthCode setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

    public AuthCode setCodeChallengeMethod(String codeChallengeMethod) {
        this.codeChallengeMethod = codeChallengeMethod;
        return this;
    }

    public String getCodeChallenge() {
        return codeChallenge;
    }

    public AuthCode setCodeChallenge(String codeChallenge) {
        this.codeChallenge = codeChallenge;
        return this;
    }
}
