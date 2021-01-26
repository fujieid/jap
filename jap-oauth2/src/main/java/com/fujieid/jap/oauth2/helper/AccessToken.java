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
package com.fujieid.jap.oauth2.helper;

/**
 * AccessToken entity class in OAuth 2.0 authorization process
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-01-25 22:06
 * @since 1.0.0
 */
public class AccessToken {

    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private String idToken;
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public AccessToken setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AccessToken setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public AccessToken setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AccessToken setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getIdToken() {
        return idToken;
    }

    public AccessToken setIdToken(String idToken) {
        this.idToken = idToken;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public AccessToken setScope(String scope) {
        this.scope = scope;
        return this;
    }
}
