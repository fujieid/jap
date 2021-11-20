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

import cn.hutool.core.util.*;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.oauth2.pkce.PkceHelper;
import com.fujieid.jap.oauth2.token.AccessToken;
import com.fujieid.jap.oauth2.token.AccessTokenHelper;
import com.xkcoding.json.util.Kv;
import com.xkcoding.json.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
 * @since 1.0.0
 */
public class Oauth2Strategy extends AbstractJapStrategy {

    public Oauth2Strategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    public Oauth2Strategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache) {
        super(japUserService, japConfig, japCache);
    }

    public Oauth2Strategy(JapUserService japUserService, JapConfig japConfig, JapUserStore japUserStore, JapCache japCache) {
        super(japUserService, japConfig, japUserStore, japCache);
    }

    /**
     * Authenticate request by delegating to a service provider using OAuth 2.0.
     *
     * @param config   OAuthConfig
     * @param request  The request to authenticate
     * @param response The response to authenticate
     */
    @Override
    public JapResponse authenticate(AuthenticateConfig config, JapHttpRequest request, JapHttpResponse response) {

        try {
            Oauth2Util.checkOauthCallbackRequest(request.getParameter("error"), request.getParameter("error_description"),
                "Oauth2strategy request failed.");
        } catch (JapOauth2Exception e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }

        JapUser sessionUser = this.checkSession(request, response);
        if (null != sessionUser) {
            return JapResponse.success(sessionUser);
        }

        try {
            this.checkAuthenticateConfig(config, OAuthConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        OAuthConfig authConfig = (OAuthConfig) config;

        try {
            Oauth2Util.checkOauthConfig(authConfig);
        } catch (JapOauth2Exception e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }

        boolean isPasswordOrClientMode = authConfig.getGrantType() == Oauth2GrantType.PASSWORD
            || authConfig.getGrantType() == Oauth2GrantType.CLIENT_CREDENTIALS;

        // If it is not a callback request, it must be a request to jump to the authorization link
        // If it is a password authorization request or a client authorization request, the token will be obtained directly
        if (!Oauth2Util.isCallback(request, authConfig) && !isPasswordOrClientMode) {
            String authorizationUrl = getAuthorizationUrl(authConfig);
            return JapResponse.success(authorizationUrl);
        } else {
            AccessToken accessToken = null;
            try {
                accessToken = AccessTokenHelper.getToken(request, authConfig);
            } catch (JapOauth2Exception e) {
                return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
            }
            JapUser japUser = null;
            try {
                japUser = getUserInfo(authConfig, accessToken);
            } catch (JapOauth2Exception e) {
                return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
            }

            if (null == japUser) {
                return JapResponse.error(JapErrorCode.UNABLE_SAVE_USERINFO);
            }
            return this.loginSuccess(japUser, request, response);
        }
    }

    /**
     * Refresh the authorized token from the OAuth service provider
     *
     * @param config       AuthenticateConfig
     * @param refreshToken The refresh_token returned with the access_token when requesting the token endpoint
     * @return JapResponse
     */
    public JapResponse refreshToken(AuthenticateConfig config, String refreshToken) {
        try {
            this.checkAuthenticateConfig(config, OAuthConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        OAuthConfig authConfig = (OAuthConfig) config;
        if (authConfig.getGrantType() != Oauth2GrantType.REFRESH_TOKEN) {
            return JapResponse.error(JapErrorCode.INVALID_GRANT_TYPE);
        }
        AccessToken accessToken = null;
        try {
            accessToken = AccessTokenHelper.getToken(null, authConfig, refreshToken);
        } catch (JapOauth2Exception e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }

        return JapResponse.success(accessToken);
    }

    /**
     * Revoke the authorized token from the OAuth service provider
     *
     * @param config      AuthenticateConfig
     * @param accessToken Authorized access_token
     * @return JapResponse
     */
    public JapResponse revokeToken(AuthenticateConfig config, String accessToken) {
        try {
            this.checkAuthenticateConfig(config, OAuthConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        OAuthConfig authConfig = (OAuthConfig) config;

        Map<String, String> params = new HashMap<>(6);
        params.put("access_token", accessToken);

        Kv tokenInfo = Oauth2Util.request(authConfig.getRevokeTokenEndpointMethodType(), authConfig.getRevokeTokenUrl(), params);

        Oauth2Util.checkOauthResponse(tokenInfo, "Oauth2Strategy failed to revoke access_token. " + accessToken);

        return JapResponse.success();
    }

    /**
     * Get the userinfo from the OAuth service provider
     *
     * @param config      AuthenticateConfig
     * @param accessToken {@link com.fujieid.jap.oauth2.token.AccessToken}
     * @return JapResponse
     */
    public JapResponse getUserInfo(AuthenticateConfig config, AccessToken accessToken) {
        try {
            this.checkAuthenticateConfig(config, OAuthConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        OAuthConfig authConfig = (OAuthConfig) config;
        try {
            return JapResponse.success(this.getUserInfo(authConfig, accessToken));
        } catch (JapOauth2Exception e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
    }

    private JapUser getUserInfo(OAuthConfig authConfig, AccessToken accessToken) throws JapOauth2Exception {
        if (null == accessToken || StringUtil.isEmpty(accessToken.getAccessToken())) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get userInfo with accessToken. AccessToken is empty.");
        }
        Map<String, String> params = new HashMap<>(3);
        params.put("access_token", accessToken.getAccessToken());

        Kv userInfo = Oauth2Util.request(authConfig.getUserInfoEndpointMethodType(), authConfig.getUserinfoUrl(), params);

        Oauth2Util.checkOauthResponse(userInfo, "Oauth2Strategy failed to get userInfo with accessToken.");

        JapUser japUser = this.japUserService.createAndGetOauth2User(authConfig.getPlatform(), userInfo, accessToken);
        if (ObjectUtil.isNull(japUser)) {
            return null;
        }
        return japUser;
    }

    private String getAuthorizationUrl(OAuthConfig authConfig) {
        String url = null;
        // 4.1.  Authorization Code Grant https://tools.ietf.org/html/rfc6749#section-4.1
        // 4.2.  Implicit Grant https://tools.ietf.org/html/rfc6749#section-4.2
        if (authConfig.getResponseType() == Oauth2ResponseType.CODE ||
            authConfig.getResponseType() == Oauth2ResponseType.TOKEN) {
            url = generateAuthorizationCodeGrantUrl(authConfig);
        }
        return url;
    }

    /**
     * It is suitable for authorization code mode(rfc6749#4.1) and implicit authorization mode(rfc6749#4.2).
     * When it is in authorization code mode, the callback requests return code and state;
     * when it is in implicit authorization mode, the callback requests return token related data
     *
     * @param authConfig oauth config
     * @return authorize request url
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1" target="_blank">4.1.  Authorization Code Grant</a>
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.2" target="_blank">4.2.  Implicit Grant</a>
     */
    private String generateAuthorizationCodeGrantUrl(OAuthConfig authConfig) {
        Map<String, Object> params = new HashMap<>(6);
        params.put("response_type", authConfig.getResponseType().getType());
        params.put("client_id", authConfig.getClientId());
        if (StrUtil.isNotBlank(authConfig.getCallbackUrl())) {
            params.put("redirect_uri", authConfig.getCallbackUrl());
        }
        if (ArrayUtil.isNotEmpty(authConfig.getScopes())) {
            params.put("scope", String.join(Oauth2Const.SCOPE_SEPARATOR, authConfig.getScopes()));
        }
        String state = authConfig.getState();
        if (StrUtil.isBlank(state)) {
            state = RandomUtil.randomString(6);
        }
        params.put("state", authConfig.getState());
        JapAuthentication.getContext().getCache().set(Oauth2Const.STATE_CACHE_KEY.concat(authConfig.getClientId()), state);
        // Pkce is only applicable to authorization code mode
        if (Oauth2ResponseType.CODE == authConfig.getResponseType() && authConfig.isEnablePkce()) {
            params.putAll(PkceHelper.generatePkceParameters(authConfig));
        }
        String query = URLUtil.buildQuery(params, StandardCharsets.UTF_8);
        return authConfig.getAuthorizationUrl().concat("?").concat(query);
    }
}
