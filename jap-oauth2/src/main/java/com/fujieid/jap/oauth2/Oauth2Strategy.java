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
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.store.SessionJapUserStore;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import com.fujieid.jap.oauth2.pkce.PkceCodeChallengeMethod;
import com.fujieid.jap.oauth2.pkce.PkceParams;
import com.fujieid.jap.oauth2.pkce.PkceUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        super(japUserService, new SessionJapUserStore(), japConfig);
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

        checkErrorResopnse(request);

        if (this.checkSession(request, response)) {
            return;
        }

        this.checkAuthenticateConfig(config, OAuthConfig.class);
        OAuthConfig oAuthConfig = (OAuthConfig) config;

        this.checkOauthConfig(oAuthConfig);

        // If it is not a callback request, it must be a request to jump to the authorization link
        if (!this.isCallback(request)) {
            redirectToAuthorizationEndPoint(response, oAuthConfig);
        } else {
            String accessToken = getAccessToken(request, oAuthConfig);
            JapUser japUser = getUserInfo(oAuthConfig, accessToken);

            this.loginSuccess(japUser, request, response);
        }

    }

    protected JapUser getUserInfo(OAuthConfig oAuthConfig, String accessToken) {
        String userinfoResponse = HttpUtil.post(oAuthConfig.getUserinfoUrl(), ImmutableMap.of("access_token", accessToken));
        JSONObject userinfo = JSONObject.parseObject(userinfoResponse);
        if (userinfo.containsKey("error") && StrUtil.isNotBlank(userinfo.getString("error"))) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get userinfo with accessToken." +
                    userinfo.getString("error_description") + " " + userinfoResponse);
        }
        JapUser japUser = this.japUserService.createAndGetOauth2User(oAuthConfig.getPlatform(), userinfo);
        if (ObjectUtil.isNull(japUser)) {
            throw new JapUserException("Unable to save user information");
        }
        return japUser;
    }

    protected String getAccessToken(HttpServletRequest request, OAuthConfig oAuthConfig) {
        String code = request.getParameter("code");
        Map<String, Object> params = Maps.newHashMap();
        params.put("grant_type", oAuthConfig.getGrantType());
        params.put("code", code);
        params.put("client_id", oAuthConfig.getClientId());
        params.put("client_secret", oAuthConfig.getClientSecret());
        if (StrUtil.isNotBlank(oAuthConfig.getCallbackUrl())) {
            params.put("redirect_uri", oAuthConfig.getCallbackUrl());
        }
        // pkce 仅适用于授权码模式
        if (Oauth2ResponseType.code == oAuthConfig.getResponseType() && oAuthConfig.isEnablePkce()) {
            params.put(PkceParams.CODE_VERIFIER, PkceUtil.getCacheCodeVerifier());
        }
        String tokenResponse = HttpUtil.post(oAuthConfig.getTokenUrl(), params);
        JSONObject accessToken = JSONObject.parseObject(tokenResponse);
        if (accessToken.containsKey("error") && StrUtil.isNotBlank(accessToken.getString("error"))) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken." +
                    accessToken.getString("error_description") + " " + tokenResponse);
        }
        if (!accessToken.containsKey("access_token")) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken." + tokenResponse);
        }
            /*
            {
               "access_token":"2YotnFZFEjr1zCsicMWpAA",
               "token_type":"example",
               "expires_in":3600,
               "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
               "example_parameter":"example_value"
             }
             */
        return accessToken.getString("access_token");
    }

    protected void redirectToAuthorizationEndPoint(HttpServletResponse response, OAuthConfig oAuthConfig) {
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
        try {
            response.sendRedirect(oAuthConfig.getAuthorizationUrl().concat("?").concat(query));
        } catch (IOException ex) {
            throw new JapException("JAP failed to redirect to " + oAuthConfig.getAuthorizationUrl() + " through HttpServletResponse.", ex);
        }
    }

    private void checkOauthConfig(OAuthConfig oAuthConfig) {
        if (ObjectUtil.isNull(oAuthConfig.getClientId())) {
            throw new JapOauth2Exception("Oauth2Strategy requires a clientId option");
        }

        if (ObjectUtil.isNull(oAuthConfig.getAuthorizationUrl())) {
            throw new JapOauth2Exception("Oauth2Strategy requires a authorizationUrl option");
        }

        if (ObjectUtil.isNull(oAuthConfig.getTokenUrl())) {
            throw new JapOauth2Exception("Oauth2Strategy requires a tokenUrl option");
        }

        if (!oAuthConfig.isEnablePkce() && ObjectUtil.isNull(oAuthConfig.getClientSecret())) {
            throw new JapOauth2Exception("Oauth2Strategy requires a clientSecret option when PKCE is not enabled.");
        }
    }

    private void checkErrorResopnse(HttpServletRequest request) {
        String error = request.getParameter("error");
        if (ObjectUtil.isNotNull(error)) {
            String errorDescription = request.getParameter("error_description");
            throw new JapOauth2Exception("Oauth2strategy request failed." + errorDescription);
        }
    }

    /**
     * Whether it is the callback request after the authorization of the oauth platform is completed,
     * the judgment basis is as follows:
     * - Code is not empty
     *
     * @param request callback request
     * @return When true is returned, the current request is a callback request
     */
    private boolean isCallback(HttpServletRequest request) {
        String code = request.getParameter("code");
        return !StrUtil.isEmpty(code);
    }
}
