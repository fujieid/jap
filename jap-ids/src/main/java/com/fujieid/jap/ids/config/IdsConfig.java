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
package com.fujieid.jap.ids.config;

import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.enums.ClientSecretAuthMethod;
import com.fujieid.jap.ids.model.enums.TokenAuthMethod;
import com.fujieid.jap.ids.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

/**
 * ids general configuration
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsConfig {

    /**
     * Get the user name from request through {@code request.getParameter(`usernameField`)}, which defaults to "username"
     */
    private String usernameField = "username";
    /**
     * Get the password from request through {@code request.getParameter(`passwordField`)}, which defaults to "password"
     */
    private String passwordField = "password";
    /**
     * Identity provider
     */
    private String issuer;
    /**
     * Login url, the default is {@code issuer + /oauth/login}
     */
    private String loginUrl;
    /**
     * Login page url, the default is {@link com.fujieid.jap.ids.config.IdsConfig#loginUrl}
     */
    private String loginPageUrl;
    /**
     * error url
     */
    private String errorUrl;
    /**
     * The user confirms the authorized url, the default is {@code issuer + /oauth/confirm}
     */
    private String confirmPageUrl;
    /**
     * Authorized url, the default is {@code issuer + /oauth/authorize}
     */
    private String authorizeUrl;
    /**
     * token url, the default is {@code issuer + /oauth/token}
     */
    private String tokenUrl;
    /**
     * userinfo url, the default is {@code issuer + /oauth/userinfo}
     */
    private String userinfoUrl;
    /**
     * Register the the client detail, the default is {@code issuer + /oauth/registration}
     */
    private String registrationUrl;
    /**
     * logout url, the default is {@code issuer + /oauth/logout}
     */
    private String endSessionUrl;
    /**
     * check session url, the default is {@code issuer + /oauth/check_session}
     */
    private String checkSessionUrl;
    /**
     * After logout, redirect to {@code logoutRedirectUrl}. Default is `/`
     */
    private String logoutRedirectUrl;
    /**
     * public key url, the default is {@code issuer + /.well-known/jwks.json}
     */
    private String jwksUrl;
    /**
     * Get open id provider metadata, the default is {@code issuer + /.well-known/openid-configuration}
     */
    private String discoveryUrl;
    /**
     * When requesting api, the way to pass token
     */
    private List<TokenAuthMethod> tokenAuthMethods = Collections.singletonList(TokenAuthMethod.ALL);
    /**
     * When requesting the token endpoint, the way to pass the client secret
     */
    private List<ClientSecretAuthMethod> clientSecretAuthMethods = Collections.singletonList(ClientSecretAuthMethod.ALL);
    /**
     * Generate/verify the global configuration of jwt token.
     * If the caller needs to configure a set of jwt config for each client,
     * you can specify jwt config when obtaining the token.
     */
    private JwtConfig jwtConfig = new JwtConfig();

    public IdsConfig(String issuer) {
        this.issuer = issuer;
    }

    public IdsConfig() {
    }

    public String getUsernameField() {
        return usernameField;
    }

    public IdsConfig setUsernameField(String usernameField) {
        this.usernameField = usernameField;
        return this;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public IdsConfig setPasswordField(String passwordField) {
        this.passwordField = passwordField;
        return this;
    }

    public String getIssuer() {
        return issuer;
    }

    public IdsConfig setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public String getLoginUrl() {
        return null == loginUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/login" : loginUrl;
    }

    public IdsConfig setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    public String getLoginPageUrl() {
        return null == loginPageUrl ? this.getLoginUrl() : loginPageUrl;
    }

    public IdsConfig setLoginPageUrl(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
        return this;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public IdsConfig setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
        return this;
    }

    public String getConfirmPageUrl() {
        return null == confirmPageUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/confirm" : confirmPageUrl;
    }

    public IdsConfig setConfirmPageUrl(String confirmPageUrl) {
        this.confirmPageUrl = confirmPageUrl;
        return this;
    }

    public String getAuthorizeUrl() {
        return null == authorizeUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/authorize" : authorizeUrl;
    }

    public IdsConfig setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
        return this;
    }

    public String getTokenUrl() {
        return null == tokenUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/token" : tokenUrl;
    }

    public IdsConfig setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    public String getUserinfoUrl() {
        return null == userinfoUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/userinfo" : userinfoUrl;
    }

    public IdsConfig setUserinfoUrl(String userinfoUrl) {
        this.userinfoUrl = userinfoUrl;
        return this;
    }

    public String getRegistrationUrl() {
        return null == registrationUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/registration" : registrationUrl;
    }

    public IdsConfig setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
        return this;
    }

    public String getEndSessionUrl() {
        return null == endSessionUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/logout" : endSessionUrl;
    }

    public IdsConfig setEndSessionUrl(String endSessionUrl) {
        this.endSessionUrl = endSessionUrl;
        return this;
    }

    public String getCheckSessionUrl() {
        return null == checkSessionUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + "oauth/check_session" : checkSessionUrl;
    }

    public IdsConfig setCheckSessionUrl(String checkSessionUrl) {
        this.checkSessionUrl = checkSessionUrl;
        return this;
    }

    public String getLogoutRedirectUrl() {
        return null == logoutRedirectUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) : logoutRedirectUrl;
    }

    public IdsConfig setLogoutRedirectUrl(String logoutRedirectUrl) {
        this.logoutRedirectUrl = logoutRedirectUrl;
        return this;
    }

    public String getJwksUrl() {
        return null == jwksUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + ".well-known/jwks.json" : jwksUrl;
    }

    public IdsConfig setJwksUrl(String jwksUrl) {
        this.jwksUrl = jwksUrl;
        return this;
    }

    public String getDiscoveryUrl() {
        return null == discoveryUrl ? ObjectUtils.appendIfNotEndWith(issuer, IdsConsts.SLASH) + ".well-known/openid-configuration" : discoveryUrl;
    }

    public IdsConfig setDiscoveryUrl(String discoveryUrl) {
        this.discoveryUrl = discoveryUrl;
        return this;
    }

    public JwtConfig getJwtConfig() {
        return null == jwtConfig ? new JwtConfig() : jwtConfig;
    }

    public IdsConfig setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        return this;
    }

    public List<TokenAuthMethod> getTokenAuthMethods() {
        return tokenAuthMethods;
    }

    public IdsConfig setTokenAuthMethods(List<TokenAuthMethod> tokenAuthMethods) {
        this.tokenAuthMethods = tokenAuthMethods;
        return this;
    }

    public List<ClientSecretAuthMethod> getClientSecretAuthMethods() {
        return clientSecretAuthMethods;
    }

    public IdsConfig setClientSecretAuthMethods(List<ClientSecretAuthMethod> clientSecretAuthMethods) {
        this.clientSecretAuthMethods = clientSecretAuthMethods;
        return this;
    }
}
