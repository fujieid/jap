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
import java.time.LocalDateTime;

/**
 * access token
 *
 * @author generate by HouTu Generator
 * @version 1.0.0
 * @since 1.0.1
 */
public class AccessToken implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String userName;
    private String grantType;
    private String scope;
    private String clientId;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
    private LocalDateTime accessTokenExpiration;
    private LocalDateTime refreshTokenExpiration;

    public String getAccessToken() {
        return accessToken;
    }

    public AccessToken setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AccessToken setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AccessToken setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public AccessToken setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public AccessToken setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public AccessToken setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public AccessToken setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Long getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public AccessToken setAccessTokenExpiresIn(Long accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        return this;
    }

    public Long getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public AccessToken setRefreshTokenExpiresIn(Long refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        return this;
    }

    public LocalDateTime getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public AccessToken setAccessTokenExpiration(LocalDateTime accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
        return this;
    }

    public LocalDateTime getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public AccessToken setRefreshTokenExpiration(LocalDateTime refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
        return this;
    }
}
