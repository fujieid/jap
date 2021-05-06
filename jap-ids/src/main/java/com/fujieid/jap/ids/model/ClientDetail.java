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
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClientDetail implements Serializable {

    private String id;

    /**
     * Application name
     */
    private String appName;

    /**
     * client id, A unique random string
     */
    private String clientId;

    /**
     * client secret, A random string
     */
    private String clientSecret;

    /**
     * Custom second-level domain name
     */
    private String siteDomain;

    /**
     * Callback url after successful login
     */
    private String redirectUri;

    /**
     * Callback url after successful logout
     */
    private String logoutRedirectUri;

    /**
     * Application logo
     */
    private String logo;

    /**
     * The status of the application, when it is false, login is not allowed
     */
    private Boolean available;

    /**
     * Application description
     */
    private String description;

    /**
     * The scope of permissions granted to the application, separated by spaces, for example: `openid email phone`
     */
    private String scopes;

    /**
     * The type of authorization granted to the application, separated by spaces
     */
    private String grantTypes;

    /**
     * The response type granted by the application, separated by spaces
     */
    private String responseTypes;

    /**
     * code authorization code valid time (seconds)
     */
    private Long codeExpiresIn;

    /**
     * id token valid time (seconds)
     */
    private Long idTokenExpiresIn;

    /**
     * Access token valid time (seconds)
     */
    private Long accessTokenExpiresIn;

    /**
     * Refresh token valid time (seconds)
     */
    private Long refreshTokenExpiresIn;

    /**
     * When {@code autoApprove} is {@code true}, the authorization is automatically approved (the page to confirm the authorization is not displayed)
     */
    private Boolean autoApprove;

    /**
     * Enable pkce enhanced protocol
     */
    private Boolean enablePkce;

    /**
     * Encryption method of pkce challenge code
     */
    private String codeChallengeMethod;

    public String getId() {
        return id;
    }

    public ClientDetail setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public ClientDetail setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientDetail setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ClientDetail setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public ClientDetail setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public ClientDetail setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public String getLogoutRedirectUri() {
        return logoutRedirectUri;
    }

    public ClientDetail setLogoutRedirectUri(String logoutRedirectUri) {
        this.logoutRedirectUri = logoutRedirectUri;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public ClientDetail setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public ClientDetail setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ClientDetail setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getScopes() {
        return scopes;
    }

    public ClientDetail setScopes(String scopes) {
        this.scopes = scopes;
        return this;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public ClientDetail setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }

    public String getResponseTypes() {
        return responseTypes;
    }

    public ClientDetail setResponseTypes(String responseTypes) {
        this.responseTypes = responseTypes;
        return this;
    }

    public Long getCodeExpiresIn() {
        return codeExpiresIn;
    }

    public ClientDetail setCodeExpiresIn(Long codeExpiresIn) {
        this.codeExpiresIn = codeExpiresIn;
        return this;
    }

    public Long getIdTokenExpiresIn() {
        return idTokenExpiresIn;
    }

    public ClientDetail setIdTokenExpiresIn(Long idTokenExpiresIn) {
        this.idTokenExpiresIn = idTokenExpiresIn;
        return this;
    }

    public Long getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public ClientDetail setAccessTokenExpiresIn(Long accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        return this;
    }

    public Long getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public ClientDetail setRefreshTokenExpiresIn(Long refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        return this;
    }

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public ClientDetail setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
        return this;
    }

    public Boolean getEnablePkce() {
        return enablePkce;
    }

    public ClientDetail setEnablePkce(Boolean enablePkce) {
        this.enablePkce = enablePkce;
        return this;
    }

    public String getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

    public ClientDetail setCodeChallengeMethod(String codeChallengeMethod) {
        this.codeChallengeMethod = codeChallengeMethod;
        return this;
    }
}
