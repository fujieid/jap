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
package com.fujieid.jap.ids.provider;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.*;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.service.Oauth2Service;
import com.fujieid.jap.ids.util.EndpointUtil;
import com.fujieid.jap.ids.util.OauthUtil;
import com.fujieid.jap.ids.util.TokenUtil;
import com.xkcoding.json.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * The token endpoint creates a token, and returns different token information for different authorization types
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsTokenProvider {
    private static final Log log = LogFactory.get();

    private final Oauth2Service oauth2Service;

    public IdsTokenProvider(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    /**
     * RFC6749 4.1. authorization code grant
     *
     * @param param   request params
     * @param request current HTTP request
     * @return IdsResponse
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1" target="_blank">4.1.  Authorization Code Grant</a>
     */
    public IdsResponse<String, Object> generateAuthorizationCodeResponse(IdsRequestParam param, HttpServletRequest request) {
        AuthCode codeInfo = oauth2Service.validateAndGetAuthrizationCode(param.getGrantType(), param.getCode());

        String scope = codeInfo.getScope();
        UserInfo userInfo = codeInfo.getUser();
        String nonce = codeInfo.getNonce();

        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());

        OauthUtil.validClientDetail(clientDetail);
        OauthUtil.validateGrantType(param.getGrantType(), clientDetail.getGrantTypes(), GrantType.AUTHORIZATION_CODE);
        OauthUtil.validateSecret(param, clientDetail, oauth2Service);
        OauthUtil.validateRedirectUri(param.getRedirectUri(), clientDetail);

        oauth2Service.invalidateCode(param.getCode());

        long expiresIn = OauthUtil.getAccessTokenExpiresIn(clientDetail.getAccessTokenExpiresIn());

        AccessToken accessToken = TokenUtil.createAccessToken(userInfo, clientDetail, param.getGrantType(), scope, nonce, EndpointUtil.getIssuer(request));
        IdsResponse<String, Object> response = new IdsResponse<String, Object>()
            .add(IdsConsts.ACCESS_TOKEN, accessToken.getAccessToken())
            .add(IdsConsts.REFRESH_TOKEN, accessToken.getRefreshToken())
            .add(IdsConsts.EXPIRES_IN, expiresIn)
            .add(IdsConsts.TOKEN_TYPE, IdsConsts.TOKEN_TYPE_BEARER)
            .add(IdsConsts.SCOPE, scope);
        if (OauthUtil.isOidcProtocol(scope)) {
            response.add(IdsConsts.ID_TOKEN, TokenUtil.createIdToken(clientDetail, userInfo, nonce, EndpointUtil.getIssuer(request)));
        }
        return response;
    }

    /**
     * RFC6749 4.3.  Resource Owner Password Credentials Grant
     *
     * @param param   request params
     * @param request current HTTP request
     * @return IdsResponse
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.3" target="_blank">4.3.  Resource Owner Password Credentials Grant</a>
     */
    public IdsResponse<String, Object> generatePasswordResponse(IdsRequestParam param, HttpServletRequest request) {
        String username = param.getUsername();
        String password = param.getPassword();
        String clientId = param.getClientId();
        UserInfo userInfo = JapIds.getContext().getUserService().loginByUsernameAndPassword(username, password, clientId);
        if (null == userInfo) {
            throw new IdsException(ErrorResponse.INVALID_USER_CERTIFICATE);
        }
        JapIds.saveUserInfo(userInfo, request);

        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());
        String requestScope = param.getScope();

        OauthUtil.validClientDetail(clientDetail);
        OauthUtil.validateScope(requestScope, clientDetail.getScopes());
        OauthUtil.validateGrantType(param.getGrantType(), clientDetail.getGrantTypes(), GrantType.PASSWORD);
        OauthUtil.validateSecret(param, clientDetail, oauth2Service);

        long expiresIn = OauthUtil.getAccessTokenExpiresIn(clientDetail.getAccessTokenExpiresIn());

        AccessToken accessToken = TokenUtil.createAccessToken(userInfo, clientDetail, param.getGrantType(), requestScope, param.getNonce(), EndpointUtil.getIssuer(request));
        IdsResponse<String, Object> response = new IdsResponse<String, Object>()
            .add(IdsConsts.ACCESS_TOKEN, accessToken.getAccessToken())
            .add(IdsConsts.REFRESH_TOKEN, accessToken.getRefreshToken())
            .add(IdsConsts.EXPIRES_IN, expiresIn)
            .add(IdsConsts.TOKEN_TYPE, IdsConsts.TOKEN_TYPE_BEARER)
            .add(IdsConsts.SCOPE, requestScope);

        if (OauthUtil.isOidcProtocol(requestScope)) {
            response.add(IdsConsts.ID_TOKEN, TokenUtil.createIdToken(clientDetail, userInfo, param.getNonce(), EndpointUtil.getIssuer(request)));
        }
        return response;
    }

    /**
     * RFC6749 4.4.  Client Credentials Grant
     *
     * @param param   request params
     * @param request current HTTP request
     * @return IdsResponse
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.4" target="_blank">4.4.  Client Credentials Grant</a>
     */
    public IdsResponse<String, Object> generateClientCredentialsResponse(IdsRequestParam param, HttpServletRequest request) {
        String clientId = param.getClientId();

        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(clientId);
        String requestScope = param.getScope();

        OauthUtil.validClientDetail(clientDetail);
        OauthUtil.validateScope(requestScope, clientDetail.getScopes());
        OauthUtil.validateGrantType(param.getGrantType(), clientDetail.getGrantTypes(), GrantType.CLIENT_CREDENTIALS);
        OauthUtil.validateSecret(param, clientDetail, oauth2Service);

        long expiresIn = OauthUtil.getAccessTokenExpiresIn(clientDetail.getAccessTokenExpiresIn());

        AccessToken accessToken = TokenUtil.createClientCredentialsAccessToken(clientDetail, param.getGrantType(), requestScope, param.getNonce(), EndpointUtil.getIssuer(request));

        // https://tools.ietf.org/html/rfc6749#section-4.2.2
        // The authorization server MUST NOT issue a refresh token.
        IdsResponse<String, Object> response = new IdsResponse<String, Object>()
            .add(IdsConsts.ACCESS_TOKEN, accessToken.getAccessToken())
            .add(IdsConsts.EXPIRES_IN, expiresIn)
            .add(IdsConsts.TOKEN_TYPE, IdsConsts.TOKEN_TYPE_BEARER);
        if (!StringUtil.isEmpty(requestScope)) {
            response.add(IdsConsts.SCOPE, requestScope);
        }
        return response;
    }

    /**
     * RFC6749 6.  Refreshing an Access Token
     *
     * @param param   request params
     * @param request current HTTP request
     * @return IdsResponse
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-6" target="_blank">6.  Refreshing an Access Token</a>
     */
    public IdsResponse<String, Object> generateRefreshTokenResponse(IdsRequestParam param, HttpServletRequest request) {
        TokenUtil.validateRefreshToken(param.getRefreshToken());

        AccessToken token = TokenUtil.getByRefreshToken(param.getRefreshToken());

        ClientDetail clientDetail = null;
        try {

            clientDetail = JapIds.getContext().getClientDetailService().getByClientId(token.getClientId());
        } catch (Exception e) {
            log.error(e);
            throw new IdsException(ErrorResponse.INVALID_CLIENT);
        }

        String requestScope = param.getScope();

        OauthUtil.validClientDetail(clientDetail);
        OauthUtil.validateScope(requestScope, clientDetail.getScopes());
        OauthUtil.validateGrantType(param.getGrantType(), clientDetail.getGrantTypes(), GrantType.REFRESH_TOKEN);
        OauthUtil.validateSecret(param, clientDetail, oauth2Service);

        UserInfo user = JapIds.getContext().getUserService().getById(token.getUserId());

        long expiresIn = OauthUtil.getRefreshTokenExpiresIn(clientDetail.getRefreshTokenExpiresIn());

        AccessToken accessToken = TokenUtil.refreshAccessToken(user, clientDetail, token, param.getNonce(), EndpointUtil.getIssuer(request));
        return new IdsResponse<String, Object>()
            .add(IdsConsts.ACCESS_TOKEN, accessToken.getAccessToken())
            .add(IdsConsts.REFRESH_TOKEN, accessToken.getRefreshToken())
            .add(IdsConsts.EXPIRES_IN, expiresIn)
            .add(IdsConsts.TOKEN_TYPE, IdsConsts.TOKEN_TYPE_BEARER)
            .add(IdsConsts.SCOPE, requestScope);
    }
}
