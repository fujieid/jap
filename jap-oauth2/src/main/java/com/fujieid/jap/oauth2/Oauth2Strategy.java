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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.*;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import com.fujieid.jap.oauth2.helper.AccessToken;
import com.fujieid.jap.oauth2.helper.AccessTokenHelper;
import com.fujieid.jap.oauth2.pkce.PkceCodeChallengeMethod;
import com.fujieid.jap.oauth2.pkce.PkceUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.xkcoding.http.HttpUtil;
import com.xkcoding.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * The OAuth 2.0 authentication strategy authenticates requests using the OAuth 2.0 framework.
 * <p>
 * OAuth 2.0 provides a facility for delegated authentication, whereby users can authenticate using a third-party service
 * such as Facebook.  Delegating in this manner involves a sequence of events, including redirecting the user to
 * the third-party service for authorization.  Once authorization has been granted, the user is redirected back to
 * the application and an authorization code can be used to obtain credentials.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/14 11:30
 * @since 1.0.0
 */
public class Oauth2Strategy extends AbstractJapStrategy {

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public Oauth2Strategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public Oauth2Strategy(JapUserService japUserService, JapUserStore japUserStore, JapConfig japConfig) {
        super(japUserService, japUserStore, japConfig);
    }

    /**
     * Authenticate request by delegating to a service provider using OAuth 2.0.
     *
     * @param config   OAuthConfig
     * @param request  The request to authenticate
     * @param response The response to authenticate
     */
    @Override
    public void authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {

        Oauth2Util.checkOauthCallbackRequest(request, "Oauth2strategy request failed.");

        if (this.checkSession(request, response)) {
            return;
        }

        this.checkAuthenticateConfig(config, OAuthConfig.class);
        OAuthConfig oAuthConfig = (OAuthConfig) config;

        this.checkOauthConfig(oAuthConfig);

        boolean isPasswordOrClientMode = oAuthConfig.getGrantType() == Oauth2GrantType.password
            || oAuthConfig.getGrantType() == Oauth2GrantType.client_credentials;

        // If it is not a callback request, it must be a request to jump to the authorization link
        // If it is a password authorization request or a client authorization request, the token will be obtained directly
        if (!this.isCallback(request, oAuthConfig) && !isPasswordOrClientMode) {
            redirectToAuthorizationEndPoint(response, oAuthConfig);
        } else {
            AccessToken accessToken = AccessTokenHelper.getToken(request, oAuthConfig);
            JapUser japUser = getUserInfo(oAuthConfig, accessToken);

            this.loginSuccess(japUser, request, response);
        }

    }

    private JapUser getUserInfo(OAuthConfig oAuthConfig, AccessToken accessToken) {
        String userinfoResponse = HttpUtil.post(oAuthConfig.getUserinfoUrl(),
            ImmutableMap.of("access_token", accessToken.getAccessToken()), false);
        Map<String, Object> userinfo = JsonUtil.toBean(userinfoResponse, Map.class);

        Oauth2Util.checkOauthResponse(userinfoResponse, userinfo, "Oauth2Strategy failed to get userinfo with accessToken.");

        JapUser japUser = this.japUserService.createAndGetOauth2User(oAuthConfig.getPlatform(), userinfo, accessToken);
        if (ObjectUtil.isNull(japUser)) {
            throw new JapUserException("Unable to save user information");
        }
        return japUser;
    }

    private void redirectToAuthorizationEndPoint(HttpServletResponse response, OAuthConfig oAuthConfig) {
        String url = null;
        // 4.1.  Authorization Code Grant https://tools.ietf.org/html/rfc6749#section-4.1
        // 4.2.  Implicit Grant https://tools.ietf.org/html/rfc6749#section-4.2
        if (oAuthConfig.getResponseType() == Oauth2ResponseType.code ||
            oAuthConfig.getResponseType() == Oauth2ResponseType.token) {
            url = generateAuthorizationCodeGrantUrl(oAuthConfig);
        }
        JapUtil.redirect(url, "JAP failed to redirect to " + oAuthConfig.getAuthorizationUrl() + " through HttpServletResponse.", response);
    }

    /**
     * It is suitable for authorization code mode(rfc6749#4.1) and implicit authorization mode(rfc6749#4.2).
     * When it is in authorization code mode, the callback requests return code and state;
     * when it is in implicit authorization mode, the callback requests return token related data
     *
     * @param oAuthConfig oauth config
     * @return authorize request url
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1" target="_blank">4.1.  Authorization Code Grant</a>
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.2" target="_blank">4.2.  Implicit Grant</a>
     */
    private String generateAuthorizationCodeGrantUrl(OAuthConfig oAuthConfig) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("response_type", oAuthConfig.getResponseType());
        params.put("client_id", oAuthConfig.getClientId());
        if (StrUtil.isNotBlank(oAuthConfig.getCallbackUrl())) {
            params.put("redirect_uri", oAuthConfig.getCallbackUrl());
        }
        if (ArrayUtil.isNotEmpty(oAuthConfig.getScopes())) {
            params.put("scope", String.join(Oauth2Const.SCOPE_SEPARATOR, oAuthConfig.getScopes()));
        }
        if (StrUtil.isNotBlank(oAuthConfig.getState())) {
            params.put("state", oAuthConfig.getState());
        }
        // Pkce is only applicable to authorization code mode
        if (Oauth2ResponseType.code == oAuthConfig.getResponseType() && oAuthConfig.isEnablePkce()) {
            PkceUtil.addPkceParameters(Optional.ofNullable(oAuthConfig.getCodeChallengeMethod())
                .orElse(PkceCodeChallengeMethod.S256), params);
        }
        String query = URLUtil.buildQuery(params, StandardCharsets.UTF_8);
        return oAuthConfig.getAuthorizationUrl().concat("?").concat(query);
    }

    /**
     * Check the validity of oauthconfig.
     * <p>
     * 1. For {@code tokenUrl}, this configuration is indispensable for any mode
     * 2. When responsetype = code:
     * - {@code authorizationUrl} and {@code userinfoUrl} cannot be null
     * - {@code clientId} cannot be null
     * - {@code clientSecret} cannot be null when PKCE is not enabled
     * 3. When responsetype = token:
     * - {@code authorizationUrl} and {@code userinfoUrl} cannot be null
     * - {@code clientId} cannot be null
     * - {@code clientSecret} cannot be null
     * 4. When GrantType = password:
     * - {@code username} and {@code password} cannot be null
     *
     * @param oAuthConfig oauth config
     */
    private void checkOauthConfig(OAuthConfig oAuthConfig) {
        if (ObjectUtil.isNull(oAuthConfig.getTokenUrl())) {
            throw new JapOauth2Exception("Oauth2Strategy requires a tokenUrl");
        }
        // For authorization code mode and implicit authorization mode
        // refer to: https://tools.ietf.org/html/rfc6749#section-4.1
        // refer to: https://tools.ietf.org/html/rfc6749#section-4.2
        if (oAuthConfig.getResponseType() == Oauth2ResponseType.code ||
            oAuthConfig.getResponseType() == Oauth2ResponseType.token) {

            if (oAuthConfig.getResponseType() == Oauth2ResponseType.code) {
                if (oAuthConfig.getGrantType() != Oauth2GrantType.authorization_code) {
                    throw new JapOauth2Exception("Invalid grantType `" + oAuthConfig.getGrantType() + "`. " +
                        "When using authorization code mode, grantType must be `authorization_code`");
                }

                if (!oAuthConfig.isEnablePkce() && ObjectUtil.isNull(oAuthConfig.getClientSecret())) {
                    throw new JapOauth2Exception("Oauth2Strategy requires a clientSecret when PKCE is not enabled.");
                }
            } else {
                if (ObjectUtil.isNull(oAuthConfig.getClientSecret())) {
                    throw new JapOauth2Exception("Oauth2Strategy requires a clientSecret");
                }

            }
            if (ObjectUtil.isNull(oAuthConfig.getClientId())) {
                throw new JapOauth2Exception("Oauth2Strategy requires a clientId");
            }

            if (ObjectUtil.isNull(oAuthConfig.getAuthorizationUrl())) {
                throw new JapOauth2Exception("Oauth2Strategy requires a authorizationUrl");
            }

            if (ObjectUtil.isNull(oAuthConfig.getUserinfoUrl())) {
                throw new JapOauth2Exception("Oauth2Strategy requires a userinfoUrl");
            }
        }
        // For password mode
        // refer to: https://tools.ietf.org/html/rfc6749#section-4.3
        else {
            if (oAuthConfig.getGrantType() == Oauth2GrantType.password) {
                if (!ObjectUtil.isAllNotEmpty(oAuthConfig.getUsername(), oAuthConfig.getPassword())) {
                    throw new JapOauth2Exception("Oauth2Strategy requires username and password in password certificate grant");
                }
            }
        }
    }

    /**
     * Whether it is the callback request after the authorization of the oauth platform is completed,
     * the judgment basis is as follows:
     * - When {@code response_type} is {@code code}, the {@code code} in the request parameter is empty
     * - When {@code response_type} is {@code token}, the {@code access_token} in the request parameter is empty
     *
     * @param request     callback request
     * @param oAuthConfig OAuthConfig
     * @return When true is returned, the current request is a callback request
     */
    private boolean isCallback(HttpServletRequest request, OAuthConfig oAuthConfig) {
        if (oAuthConfig.getResponseType() == Oauth2ResponseType.code) {
            String code = request.getParameter("code");
            return !StrUtil.isEmpty(code);
        } else if (oAuthConfig.getResponseType() == Oauth2ResponseType.token) {
            String accessToken = request.getParameter("access_token");
            return !StrUtil.isEmpty(accessToken);
        }
        return false;
    }
}
