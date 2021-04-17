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

import com.xkcoding.json.util.StringUtil;

/**
 * Parameters of oauth request
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsRequestParam {
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String code;
    private String redirectUri;
    private String scope;
    private String state;
    private String accessToken;
    private String refreshToken;
    private String responseType;
    private String uid;
    private String autoapprove;
    private String username;
    private String password;
    private String codeVerifier;
    private String codeChallengeMethod;
    private String codeChallenge;

    /*  The following are the parameters supported by the oidc protocol, referenced from: https://openid.net/specs/openid-connect-core-1_0.html#AuthRequest     */

    /**
     * optional, The nonce parameter value needs to include per-session state and be unguessable to attackers
     */
    private String nonce;
    /**
     * Optional. The newly defined parameter of oidc (oauth 2.0 form post response mode) is used to specify how the authorization endpoint returns data.
     */
    private String responseMode;
    /**
     * OPTIONAL. ASCII string value that specifies how the Authorization Server displays the authentication and consent user interface pages to the End-User. The defined values are:
     * <p>
     * <strong>page</strong> - The Authorization Server SHOULD display the authentication and consent UI consistent with a full User Agent page view.
     * If the display parameter is not specified, this is the default display mode.
     * <p>
     * <strong>popup</strong> - The Authorization Server SHOULD display the authentication and consent UI consistent with a popup User Agent window.
     * The popup User Agent window should be of an appropriate size for a login-focused dialog and should not obscure the entire window that it is popping up over.
     * <p>
     * <strong>touch</strong> - The Authorization Server SHOULD display the authentication and consent UI consistent with a device that leverages a touch interface.
     * <p>
     * <strong>wap</strong> - The Authorization Server SHOULD display the authentication and consent UI consistent with a "feature phone" type display.
     */
    private String display;
    /**
     * OPTIONAL. Space delimited, case sensitive list of ASCII string values that specifies whether the Authorization Server prompts the End-User for reauthentication and consent. The defined values are:
     * <p>
     * <strong>none</strong> - The Authorization Server MUST NOT display any authentication or consent user interface pages.
     * An error is returned if an End-User is not already authenticated or the Client does not have pre-configured
     * consent for the requested Claims or does not fulfill other conditions for processing the request.
     * The error code will typically be login_required, interaction_required, or another code defined in Section 3.1.2.6.
     * This can be used as a method to check for existing authentication and/or consent.
     * <p>
     * <strong>login</strong> - The Authorization Server SHOULD prompt the End-User for reauthentication.
     * If it cannot reauthenticate the End-User, it MUST return an error, typically login_required.
     * <p>
     * <strong>consent</strong> - The Authorization Server SHOULD prompt the End-User for consent before returning information to the Client.
     * If it cannot obtain consent, it MUST return an error, typically consent_required.
     * <p>
     * <strong>select_account</strong> - The Authorization Server SHOULD prompt the End-User to select a user account.
     * This enables an End-User who has multiple accounts at the Authorization Server to select amongst
     * the multiple accounts that they might have current sessions for.
     * If it cannot obtain an account selection choice made by the End-User,
     * it MUST return an error, typically account_selection_required.
     */
    private String prompt;
    /**
     * Optional. Represents the valid time of the eu authentication information,
     * corresponding to the claim of auth time in the id token. For example,
     * if the setting is 20 minutes, if the time is exceeded, you need to guide eu to re-authenticate.
     */
    private String authTime;
    /**
     * Optional. For the previously issued id token, if the id token is verified and valid, it needs to return a normal response;
     * if there is an error, it returns a corresponding error prompt.
     */
    private String idTokenHint;
    /**
     * Optional. Requested Authentication Context Class Reference values.
     * Space-separated string that specifies the acr values that the Authorization Server is being requested to use for processing this Authentication Request,
     * with the values appearing in order of preference
     */
    private String acr;

    public boolean isEnablePkce() {
        return !StringUtil.isEmpty(this.getCodeVerifier());
    }

    public String getClientId() {
        return clientId;
    }

    public IdsRequestParam setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public IdsRequestParam setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public IdsRequestParam setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getCode() {
        return code;
    }

    public IdsRequestParam setCode(String code) {
        this.code = code;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public IdsRequestParam setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public IdsRequestParam setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getState() {
        return state;
    }

    public IdsRequestParam setState(String state) {
        this.state = state;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public IdsRequestParam setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public IdsRequestParam setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getResponseType() {
        return responseType;
    }

    public IdsRequestParam setResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public IdsRequestParam setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public IdsRequestParam setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getResponseMode() {
        return responseMode;
    }

    public IdsRequestParam setResponseMode(String responseMode) {
        this.responseMode = responseMode;
        return this;
    }

    public String getDisplay() {
        return display;
    }

    public IdsRequestParam setDisplay(String display) {
        this.display = display;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public IdsRequestParam setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public String getAuthTime() {
        return authTime;
    }

    public IdsRequestParam setAuthTime(String authTime) {
        this.authTime = authTime;
        return this;
    }

    public String getIdTokenHint() {
        return idTokenHint;
    }

    public IdsRequestParam setIdTokenHint(String idTokenHint) {
        this.idTokenHint = idTokenHint;
        return this;
    }

    public String getAcr() {
        return acr;
    }

    public IdsRequestParam setAcr(String acr) {
        this.acr = acr;
        return this;
    }

    public String getAutoapprove() {
        return autoapprove;
    }

    public IdsRequestParam setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public IdsRequestParam setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IdsRequestParam setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCodeVerifier() {
        return codeVerifier;
    }

    public IdsRequestParam setCodeVerifier(String codeVerifier) {
        this.codeVerifier = codeVerifier;
        return this;
    }

    public String getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

    public IdsRequestParam setCodeChallengeMethod(String codeChallengeMethod) {
        this.codeChallengeMethod = codeChallengeMethod;
        return this;
    }

    public String getCodeChallenge() {
        return codeChallenge;
    }

    public IdsRequestParam setCodeChallenge(String codeChallenge) {
        this.codeChallenge = codeChallenge;
        return this;
    }
}
