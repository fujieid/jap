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
package com.fujieid.jap.oauth2;

import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.oauth2.pkce.PkceCodeChallengeMethod;

/**
 * Configuration file of oauth2 module
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class OAuthConfig extends AuthenticateConfig {

    /**
     * Name of OAuth platform
     */
    private String platform;
    /**
     * identifies client to service provider
     */
    private String clientId;

    /**
     * secret used to establish ownership of the client identifer
     */
    private String clientSecret;

    /**
     * URL to which the service provider will redirect the user after obtaining authorization
     */
    private String callbackUrl;

    /**
     * URL used to obtain an authorization grant
     */
    private String authorizationUrl;

    /**
     * URL used to obtain an access token
     */
    private String tokenUrl;

    /**
     * URL used to refresh access_token
     */
    private String refreshTokenUrl;

    /**
     * URL used to revoke access_token
     */
    private String revokeTokenUrl;

    /**
     * URL used to obtain an userinfo
     */
    private String userinfoUrl;

    /**
     * The value MUST be one of "code" for requesting an
     * authorization code as described by Section 4.1.1 (<a href="https://tools.ietf.org/html/rfc6749#section-4.1.1" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.1.1</a>),
     * "token" for requesting an access token (implicit grant) as described by Section 4.2.1 (<a href="https://tools.ietf.org/html/rfc6749#section-4.2.1" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.2.1</a>),
     * or a registered extension value as described by Section 8.4 (<a href="https://tools.ietf.org/html/rfc6749#section-8.4" target="_blank">https://tools.ietf.org/html/rfc6749#section-8.4</a>).
     */
    private Oauth2ResponseType responseType = Oauth2ResponseType.NONE;

    /**
     * The optional value is: {@code AUTHORIZATION_CODE}, {@code PASSWORD}, {@code CLIENT_CREDENTIALS}
     */
    private Oauth2GrantType grantType = Oauth2GrantType.AUTHORIZATION_CODE;

    /**
     * The scope supported by the OAuth platform
     */
    private String[] scopes;

    /**
     * An opaque value used by the client to maintain
     * state between the request and callback.  The authorization
     * server includes this value when redirecting the user-agent back
     * to the client.
     */
    private String state;

    /**
     * The scope supported by the OAuth platform
     */
    private boolean enablePkce;

    /**
     * After the pkce enhancement protocol is enabled, the generation method of challenge code derived from
     * the code verifier sent in the authorization request is `s256` by default
     *
     * @see <a href="https://tools.ietf.org/html/rfc7636#section-4.3" target="_blank"> Client Sends the Code Challenge with the Authorization Request</a>
     */
    private PkceCodeChallengeMethod codeChallengeMethod = PkceCodeChallengeMethod.S256;

    /**
     * The username in `Resource Owner Password Credentials Grant`
     *
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.3" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.3</a>
     */
    private String username;

    /**
     * The password in `Resource Owner Password Credentials Grant`
     *
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.3" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.3</a>
     */
    private String password;

    /**
     * In pkce mode, the expiration time of codeverifier, in milliseconds, default is 3 minutes
     */
    private long codeVerifierTimeout = 180000;

    /**
     * When {@code verifyState} is true, it will check whether the state in authorization request is consistent with that in callback request
     */
    private boolean verifyState = true;

    /**
     * method of userInfo endpoint, default is POST
     * <p>
     * Different third-party platforms may use different request methods,
     * and some third-party platforms have limited request methods, such as post and get.
     */
    private Oauth2EndpointMethodType userInfoEndpointMethodType = Oauth2EndpointMethodType.POST;
    /**
     * method of accessToken endpoint, default is POST
     * <p>
     * Different third-party platforms may use different request methods,
     * and some third-party platforms have limited request methods, such as post and get.
     */
    private Oauth2EndpointMethodType accessTokenEndpointMethodType = Oauth2EndpointMethodType.POST;
    /**
     * method of refreshToken endpoint, default is POST
     * <p>
     * Different third-party platforms may use different request methods,
     * and some third-party platforms have limited request methods, such as post and get.
     */
    private Oauth2EndpointMethodType refreshTokenEndpointMethodType = Oauth2EndpointMethodType.POST;
    /**
     * method of revokeToken endpoint, default is POST
     * <p>
     * Different third-party platforms may use different request methods,
     * and some third-party platforms have limited request methods, such as post and get.
     */
    private Oauth2EndpointMethodType revokeTokenEndpointMethodType = Oauth2EndpointMethodType.POST;

    public String getClientId() {
        return clientId;
    }

    public OAuthConfig setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public OAuthConfig setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public OAuthConfig setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public OAuthConfig setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
        return this;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public OAuthConfig setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public OAuthConfig setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
        return this;
    }

    public String getRevokeTokenUrl() {
        return revokeTokenUrl;
    }

    public OAuthConfig setRevokeTokenUrl(String revokeTokenUrl) {
        this.revokeTokenUrl = revokeTokenUrl;
        return this;
    }

    public String[] getScopes() {
        return scopes;
    }

    public OAuthConfig setScopes(String[] scopes) {
        this.scopes = scopes;
        return this;
    }

    public boolean isEnablePkce() {
        return enablePkce;
    }

    public OAuthConfig setEnablePkce(boolean enablePkce) {
        this.enablePkce = enablePkce;
        return this;
    }

    public PkceCodeChallengeMethod getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

    public OAuthConfig setCodeChallengeMethod(PkceCodeChallengeMethod codeChallengeMethod) {
        this.codeChallengeMethod = codeChallengeMethod;
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public OAuthConfig setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getState() {
        return state;
    }

    public OAuthConfig setState(String state) {
        this.state = state;
        return this;
    }

    public Oauth2ResponseType getResponseType() {
        return responseType;
    }

    public OAuthConfig setResponseType(Oauth2ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getUserinfoUrl() {
        return userinfoUrl;
    }

    public OAuthConfig setUserinfoUrl(String userinfoUrl) {
        this.userinfoUrl = userinfoUrl;
        return this;
    }

    public Oauth2GrantType getGrantType() {
        return grantType;
    }

    public OAuthConfig setGrantType(Oauth2GrantType grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public OAuthConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OAuthConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public long getCodeVerifierTimeout() {
        return codeVerifierTimeout;
    }

    public OAuthConfig setCodeVerifierTimeout(long codeVerifierTimeout) {
        this.codeVerifierTimeout = codeVerifierTimeout;
        return this;
    }

    public boolean isVerifyState() {
        return verifyState;
    }

    public OAuthConfig setVerifyState(boolean verifyState) {
        this.verifyState = verifyState;
        return this;
    }

    public Oauth2EndpointMethodType getUserInfoEndpointMethodType() {
        return userInfoEndpointMethodType;
    }

    public OAuthConfig setUserInfoEndpointMethodType(Oauth2EndpointMethodType userInfoEndpointMethodType) {
        this.userInfoEndpointMethodType = userInfoEndpointMethodType;
        return this;
    }

    public Oauth2EndpointMethodType getAccessTokenEndpointMethodType() {
        return accessTokenEndpointMethodType;
    }

    public OAuthConfig setAccessTokenEndpointMethodType(Oauth2EndpointMethodType accessTokenEndpointMethodType) {
        this.accessTokenEndpointMethodType = accessTokenEndpointMethodType;
        return this;
    }

    public Oauth2EndpointMethodType getRefreshTokenEndpointMethodType() {
        return refreshTokenEndpointMethodType;
    }

    public OAuthConfig setRefreshTokenEndpointMethodType(Oauth2EndpointMethodType refreshTokenEndpointMethodType) {
        this.refreshTokenEndpointMethodType = refreshTokenEndpointMethodType;
        return this;
    }

    public Oauth2EndpointMethodType getRevokeTokenEndpointMethodType() {
        return revokeTokenEndpointMethodType;
    }

    public OAuthConfig setRevokeTokenEndpointMethodType(Oauth2EndpointMethodType revokeTokenEndpointMethodType) {
        this.revokeTokenEndpointMethodType = revokeTokenEndpointMethodType;
        return this;
    }
}
