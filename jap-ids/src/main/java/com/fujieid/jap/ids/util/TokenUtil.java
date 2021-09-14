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
package com.fujieid.jap.ids.util;

import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.util.RequestUtil;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.IdsTokenException;
import com.fujieid.jap.ids.exception.InvalidTokenException;
import com.fujieid.jap.ids.model.*;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.TokenAuthMethod;
import com.fujieid.jap.ids.service.IdsTokenService;
import com.xkcoding.json.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class TokenUtil {

    /**
     * Get access token from request
     *
     * @param request request
     * @return String
     */
    public static String getAccessToken(HttpServletRequest request) {
        List<TokenAuthMethod> tokenAuthMethods = JapIds.getIdsConfig().getTokenAuthMethods();
        if (ObjectUtil.isEmpty(tokenAuthMethods)) {
            tokenAuthMethods = Collections.singletonList(TokenAuthMethod.ALL);
        }
        if (tokenAuthMethods.contains(TokenAuthMethod.ALL)) {
            String accessToken = getAccessTokenFromUrl(request);
            if (StringUtil.isEmpty(accessToken)) {
                accessToken = getAccessTokenFromHeader(request);
                if (StringUtil.isEmpty(accessToken)) {
                    accessToken = getAccessTokenFromCookie(request);
                }
            }
            return accessToken;
        } else {
            if (tokenAuthMethods.contains(TokenAuthMethod.TOKEN_URL)) {
                String accessToken = getAccessTokenFromUrl(request);
                if (accessToken != null) {
                    return accessToken;
                }
            }
            if (tokenAuthMethods.contains(TokenAuthMethod.TOKEN_HEADER)) {
                String accessToken = getAccessTokenFromHeader(request);
                if (accessToken != null) {
                    return accessToken;
                }
            }
            if (tokenAuthMethods.contains(TokenAuthMethod.TOKEN_COOKIE)) {
                return getAccessTokenFromCookie(request);
            }
        }

        return null;
    }

    private static String getAccessTokenFromUrl(HttpServletRequest request) {
        String accessToken = RequestUtil.getParam(IdsConsts.ACCESS_TOKEN, request);
        if (StringUtil.isNotEmpty(accessToken)) {
            return accessToken;
        }
        return null;
    }

    private static String getAccessTokenFromHeader(HttpServletRequest request) {
        String accessToken = RequestUtil.getHeader(IdsConsts.AUTHORIZATION_HEADER_NAME, request);
        return BearerToken.parse(accessToken);
    }

    private static String getAccessTokenFromCookie(HttpServletRequest request) {
        return RequestUtil.getCookieVal(request, IdsConsts.ACCESS_TOKEN);
    }

    public static String createIdToken(ClientDetail clientDetail, UserInfo user, String nonce, String issuer) {
        long idTokenExpiresIn = OauthUtil.getIdTokenExpiresIn(clientDetail.getIdTokenExpiresIn());
        return JwtUtil.createJwtToken(clientDetail.getClientId(), user, idTokenExpiresIn, nonce, issuer);
    }

    public static String createIdToken(ClientDetail clientDetail, UserInfo user, IdsRequestParam param, String issuer) {
        long idTokenExpiresIn = OauthUtil.getIdTokenExpiresIn(clientDetail.getIdTokenExpiresIn());
        return JwtUtil.createJwtToken(clientDetail.getClientId(), user, idTokenExpiresIn, param.getNonce(), OauthUtil.convertStrToList(param.getScope()), param.getResponseType(), issuer);
    }

    public static AccessToken createAccessToken(UserInfo user, ClientDetail clientDetail, String grantType, String scope, String nonce, String issuer) {
        String clientId = clientDetail.getClientId();

        long accessTokenExpiresIn = OauthUtil.getAccessTokenExpiresIn(clientDetail.getAccessTokenExpiresIn());
        long refreshTokenExpiresIn = OauthUtil.getRefreshTokenExpiresIn(clientDetail.getRefreshTokenExpiresIn());

        IdsTokenService tokenService = JapIds.getContext().getTokenService();
        if (null == tokenService) {
            throw new IdsTokenException("com.fujieid.jap.ids.service.IdsTokenService has not been injected");
        }
        String accessTokenStr = tokenService.createAccessToken(clientId, user, accessTokenExpiresIn, nonce, issuer, null);
        String refreshTokenStr = tokenService.createRefreshToken(clientId, OauthUtil.convertStrToList(scope));

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(accessTokenStr);
        accessToken.setRefreshToken(refreshTokenStr);
        accessToken.setGrantType(grantType);
        if (null != user) {
            accessToken.setUserName(user.getUsername());
            accessToken.setUserId(user.getId());
        }
        accessToken.setClientId(clientId);
        accessToken.setScope(scope);

        accessToken.setRefreshTokenExpiresIn(refreshTokenExpiresIn);
        accessToken.setAccessTokenExpiresIn(accessTokenExpiresIn);

        accessToken.setAccessTokenExpiration(OauthUtil.getAccessTokenExpiresAt(accessTokenExpiresIn));
        accessToken.setRefreshTokenExpiration(OauthUtil.getRefreshTokenExpiresAt(refreshTokenExpiresIn));

        String tokenCacheKey = IdsConsts.OAUTH_ACCESS_TOKEN_CACHE_KEY + accessTokenStr;
        String rtokenCacheKey = IdsConsts.OAUTH_REFRESH_TOKEN_CACHE_KEY + refreshTokenStr;
        JapIds.getContext().getCache().set(tokenCacheKey, accessToken, accessTokenExpiresIn * 1000);
        JapIds.getContext().getCache().set(rtokenCacheKey, accessToken, refreshTokenExpiresIn * 1000);
        return accessToken;
    }

    public static AccessToken refreshAccessToken(UserInfo user, ClientDetail clientDetail, AccessToken accessToken, String nonce, String issuer) {
        String rawToken = accessToken.getAccessToken();
        Long accessTokenExpiresIn = OauthUtil.getAccessTokenExpiresIn(clientDetail.getAccessTokenExpiresIn());

        IdsTokenService tokenService = JapIds.getContext().getTokenService();
        if (null == tokenService) {
            throw new IdsTokenException("com.fujieid.jap.ids.service.IdsTokenService has not been injected");
        }
        String accessTokenStr = tokenService.createAccessToken(clientDetail.getClientId(), user, accessTokenExpiresIn, nonce, issuer, null);
        accessToken.setAccessToken(accessTokenStr);
        accessToken.setAccessTokenExpiresIn(accessTokenExpiresIn);

        accessToken.setAccessTokenExpiration(OauthUtil.getAccessTokenExpiresAt(accessTokenExpiresIn));

        String tokenCacheKey = IdsConsts.OAUTH_ACCESS_TOKEN_CACHE_KEY + accessTokenStr;
        JapIds.getContext().getCache().set(tokenCacheKey, accessToken, accessTokenExpiresIn * 1000);

        String rawTokenCacheKey = IdsConsts.OAUTH_ACCESS_TOKEN_CACHE_KEY + rawToken;
        JapIds.getContext().getCache().removeKey(rawTokenCacheKey);
        return accessToken;
    }

    public static AccessToken createClientCredentialsAccessToken(ClientDetail clientDetail, String grantType, String scope, String nonce, String issuer) {
        return createAccessToken(null, clientDetail, grantType, scope, nonce, issuer);
    }


    public static void invalidateToken(HttpServletRequest request) {
        String accessTokenStr = TokenUtil.getAccessToken(request);
        AccessToken accessToken = TokenUtil.getByAccessToken(accessTokenStr);
        if (null != accessToken) {
            String token = IdsConsts.OAUTH_ACCESS_TOKEN_CACHE_KEY + accessTokenStr;
            String rtoken = IdsConsts.OAUTH_REFRESH_TOKEN_CACHE_KEY + accessToken.getRefreshToken();
            JapIds.getContext().getCache().removeKey(token);
            JapIds.getContext().getCache().removeKey(rtoken);
        }
    }

    public static void validateAccessToken(String accessToken) {

        AccessToken token = getByAccessToken(accessToken);

        if (token == null) {
            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }

        LocalDateTime nowDateTime = DateUtil.nowDate();

        if (token.getAccessTokenExpiration().isBefore(nowDateTime)) {
            throw new InvalidTokenException(ErrorResponse.EXPIRED_TOKEN);
        }

    }

    public static void validateRefreshToken(String refreshToken) {

        AccessToken token = getByRefreshToken(refreshToken);

        if (token == null) {
            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }

        LocalDateTime nowDateTime = DateUtil.nowDate();

        if (token.getRefreshTokenExpiration().isBefore(nowDateTime)) {
            throw new InvalidTokenException(ErrorResponse.EXPIRED_TOKEN);
        }
    }

    public static AccessToken getByAccessToken(String accessToken) {
        if (null == accessToken) {
            return null;
        }
        accessToken = BearerToken.parse(accessToken);
        String token = IdsConsts.OAUTH_ACCESS_TOKEN_CACHE_KEY + accessToken;
        return (AccessToken) JapIds.getContext().getCache().get(token);
    }

    public static AccessToken getByRefreshToken(String refreshToken) {
        if (null == refreshToken) {
            return null;
        }
        String token = IdsConsts.OAUTH_REFRESH_TOKEN_CACHE_KEY + refreshToken;
        return (AccessToken) JapIds.getContext().getCache().get(token);
    }
}
