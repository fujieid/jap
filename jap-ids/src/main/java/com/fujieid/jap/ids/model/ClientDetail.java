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
     * 应用名
     */
    private String appName;

    /**
     * 应用ID
     */
    private String clientId;

    /**
     * 应用密钥
     */
    private String clientSecret;

    /**
     * 自定义二级域名
     */
    private String siteDomain;

    /**
     * 认证成功后跳转的地址
     */
    private String redirectUri;

    /**
     * 退出成功后跳转的地址
     */
    private String logoutRedirectUri;

    /**
     * 应用图标
     */
    private String logo;

    /**
     * 是否可用
     */
    private Boolean available;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 权限范围
     */
    private String scopes;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 返回类型
     */
    private String responseTypes;

    /**
     * code 授权码有效时间（秒）
     */
    private Long codeExpiresIn;

    /**
     * id token有效时间（秒）
     */
    private Long idTokenExpiresIn;

    /**
     * access token有效时间（秒）
     */
    private Long accessTokenExpiresIn;

    /**
     * refresh token有效时间（秒）
     */
    private Long refreshTokenExpiresIn;

    /**
     * 附加信息
     */
    private String additionalInformation;

    /**
     * 自动批准(不显示确认页面)
     */
    private Boolean autoApprove;

    /**
     * 启用 PKCE 增强协议
     */
    private Boolean enablePkce;

    /**
     * PKCE 质询码的加密方式
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

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public ClientDetail setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
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
