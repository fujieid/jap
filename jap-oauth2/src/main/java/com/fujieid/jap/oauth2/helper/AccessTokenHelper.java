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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.JapUtil;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.oauth2.*;
import com.fujieid.jap.oauth2.pkce.PkceParams;
import com.fujieid.jap.oauth2.pkce.PkceUtil;
import com.google.common.collect.Maps;
import com.xkcoding.http.HttpUtil;
import com.xkcoding.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Access token helper. Provides a unified access token method {@link AccessTokenHelper#getToken(HttpServletRequest, OAuthConfig)}
 * for different authorization methods
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class AccessTokenHelper {

    /**
     * get access_token
     *
     * @param request     Current callback request
     * @param oAuthConfig oauth config
     * @return AccessToken
     */
    public static AccessToken getToken(HttpServletRequest request, OAuthConfig oAuthConfig) {
        if (oAuthConfig.getResponseType() == Oauth2ResponseType.code) {
            return getAccessTokenOfAuthorizationCodeMode(request, oAuthConfig);
        }
        if (oAuthConfig.getResponseType() == Oauth2ResponseType.token) {
            return getAccessTokenOfImplicitMode(request);
        }
        if (oAuthConfig.getGrantType() == Oauth2GrantType.password) {
            return getAccessTokenOfPasswordMode(request, oAuthConfig);
        }
        if (oAuthConfig.getGrantType() == Oauth2GrantType.client_credentials) {
            return getAccessTokenOfClientMode(request, oAuthConfig);
        }
        throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken.");
    }


    /**
     * 4.1.  Authorization Code Grant
     *
     * @param request     current callback request
     * @param oAuthConfig oauth config
     * @return token request url
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1" target="_blank">4.1.  Authorization Code Grant</a>
     */
    private static AccessToken getAccessTokenOfAuthorizationCodeMode(HttpServletRequest request, OAuthConfig oAuthConfig) {
        String code = request.getParameter("code");
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", Oauth2GrantType.authorization_code.name());
        params.put("code", code);
        params.put("client_id", oAuthConfig.getClientId());
        params.put("client_secret", oAuthConfig.getClientSecret());
        if (StrUtil.isNotBlank(oAuthConfig.getCallbackUrl())) {
            params.put("redirect_uri", oAuthConfig.getCallbackUrl());
        }
        // Pkce is only applicable to authorization code mode
        if (Oauth2ResponseType.code == oAuthConfig.getResponseType() && oAuthConfig.isEnablePkce()) {
            params.put(PkceParams.CODE_VERIFIER, PkceUtil.getCacheCodeVerifier());
        }

        String tokenResponse = HttpUtil.post(oAuthConfig.getTokenUrl(), params, false);
        Map<String, Object> tokenMap = JsonUtil.toBean(tokenResponse, Map.class);
        Oauth2Util.checkOauthResponse(tokenResponse, tokenMap, "Oauth2Strategy failed to get AccessToken.");

        if (!tokenMap.containsKey("access_token")) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken." + tokenResponse);
        }

        return mapToAccessToken(tokenMap);
    }

    /**
     * 4.2.  Implicit Grant
     *
     * @param request current callback request
     * @return token request url
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.2" target="_blank">4.2.  Implicit Grant</a>
     */
    private static AccessToken getAccessTokenOfImplicitMode(HttpServletRequest request) {
        Oauth2Util.checkOauthCallbackRequest(request, "Oauth2Strategy failed to get AccessToken.");

        if (null == request.getParameter("access_token")) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken.");
        }

        return new AccessToken()
            .setAccessToken(request.getParameter("access_token"))
            .setRefreshToken(request.getParameter("refresh_token"))
            .setIdToken(request.getParameter("id_token"))
            .setTokenType(request.getParameter("token_type"))
            .setScope(request.getParameter("scope"))
            .setExpiresIn(JapUtil.convertToInt(request.getParameter("expires_in")));
    }

    /**
     * 4.3.  Resource Owner Password Credentials Grant
     *
     * @param oAuthConfig oauth config
     * @return token request url
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.3" target="_blank">4.3.  Resource Owner Password Credentials Grant</a>
     */
    private static AccessToken getAccessTokenOfPasswordMode(HttpServletRequest request, OAuthConfig oAuthConfig) {
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", Oauth2GrantType.password.name());
        params.put("username", oAuthConfig.getUsername());
        params.put("password", oAuthConfig.getPassword());
        params.put("client_id", oAuthConfig.getClientId());
        params.put("client_secret", oAuthConfig.getClientSecret());
        if (ArrayUtil.isNotEmpty(oAuthConfig.getScopes())) {
            params.put("scope", String.join(Oauth2Const.SCOPE_SEPARATOR, oAuthConfig.getScopes()));
        }
        String url = oAuthConfig.getTokenUrl();
        String tokenResponse = HttpUtil.post(url, params, false);
        Map<String, Object> tokenMap = JsonUtil.toBean(tokenResponse, Map.class);
        Oauth2Util.checkOauthResponse(tokenResponse, tokenMap, "Oauth2Strategy failed to get AccessToken.");

        if (!tokenMap.containsKey("access_token")) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken." + tokenResponse);
        }
        return mapToAccessToken(tokenMap);
    }

    /**
     * 4.4.  Client Credentials Grant
     *
     * @param oAuthConfig oauth config
     * @return token request url
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.4" target="_blank">4.4.  Client Credentials Grant</a>
     */
    private static AccessToken getAccessTokenOfClientMode(HttpServletRequest request, OAuthConfig oAuthConfig) {
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", Oauth2GrantType.client_credentials.name());
        if (ArrayUtil.isNotEmpty(oAuthConfig.getScopes())) {
            params.put("scope", String.join(Oauth2Const.SCOPE_SEPARATOR, oAuthConfig.getScopes()));
        }
        String url = oAuthConfig.getTokenUrl();

        String tokenResponse = HttpUtil.post(url, params, false);
        Map<String, Object> tokenMap = JsonUtil.toBean(tokenResponse, Map.class);
        Oauth2Util.checkOauthResponse(tokenResponse, tokenMap, "Oauth2Strategy failed to get AccessToken.");

        if (ObjectUtil.isEmpty(request.getParameter("access_token"))) {
            throw new JapOauth2Exception("Oauth2Strategy failed to get AccessToken.");
        }

        return mapToAccessToken(tokenMap);
    }

    private static AccessToken mapToAccessToken(Map<String, Object> tokenMap) {
        Object accessToken = tokenMap.get("access_token");
        Object refreshToken = tokenMap.get("refresh_token");
        Object idToken = tokenMap.get("id_token");
        Object tokenType = tokenMap.get("token_type");
        Object expiresIn = tokenMap.get("expires_in");
        Object scope = tokenMap.get("scope");
        return new AccessToken()
            .setAccessToken(JapUtil.convertToStr(accessToken))
            .setRefreshToken(JapUtil.convertToStr(refreshToken))
            .setIdToken(JapUtil.convertToStr(idToken))
            .setTokenType(JapUtil.convertToStr(tokenType))
            .setScope(JapUtil.convertToStr(scope))
            .setExpiresIn(JapUtil.convertToInt(expiresIn));
    }
}
