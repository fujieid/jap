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

import com.fujieid.jap.ids.model.*;
import com.fujieid.jap.ids.service.Oauth2Service;
import com.fujieid.jap.ids.util.OauthUtil;
import com.fujieid.jap.ids.util.ObjectUtils;
import com.fujieid.jap.ids.util.TokenUtil;
import com.xkcoding.json.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Authorize the endpoint to create a callback url, and pass different callback parameters according to the request parameters
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsAuthorizationProvider {

    private final Oauth2Service oauth2Service;

    public IdsAuthorizationProvider(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    /**
     * 4.2.  Implicit Grant
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @param issuer       The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.2">4.2.  Implicit Grant</a>
     */
    public String generateImplicitGrantResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail, String issuer) {
        AccessToken accessToken = TokenUtil.createAccessToken(userInfo, clientDetail, param.getGrantType(), param.getScope(), param.getNonce(), issuer);
        Map<String, String> tokenResponse = new HashMap<>(9);
        // https://tools.ietf.org/html/rfc6749#section-4.2.2
        // The authorization server MUST NOT issue a refresh token.
        tokenResponse.put(IdsConsts.ACCESS_TOKEN, accessToken.getAccessToken());
        tokenResponse.put(IdsConsts.EXPIRES_IN, String.valueOf(OauthUtil.getAccessTokenExpiresIn(clientDetail.getAccessTokenExpiresIn())));
        tokenResponse.put(IdsConsts.TOKEN_TYPE, IdsConsts.TOKEN_TYPE_BEARER);
        tokenResponse.put(IdsConsts.SCOPE, param.getScope());
        if (OauthUtil.isOidcProtocol(param.getScope())) {
            tokenResponse.put(IdsConsts.ID_TOKEN, TokenUtil.createIdToken(clientDetail, userInfo, param.getNonce(), issuer));
        }
        if (StringUtil.isNotEmpty(param.getState())) {
            tokenResponse.put(IdsConsts.STATE, param.getState());
        }
        String params = ObjectUtils.parseMapToString(tokenResponse, false);
        return param.getRedirectUri() + "?" + params;
    }

    /**
     * 4.1.  Authorization Code Grant
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @return String
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1">4.1.  Authorization Code Grant</a>
     */
    public String generateAuthorizationCodeResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail) {
        String authorizationCode = oauth2Service.createAuthorizationCode(param, userInfo, OauthUtil.getCodeExpiresIn(clientDetail.getCodeExpiresIn()));
        String params = "?code=" + authorizationCode;
        if (StringUtil.isNotEmpty(param.getState())) {
            params = params + "&state=" + param.getState();
        }
        return param.getRedirectUri() + params;
    }

    /**
     * When the value of {@code response_type} is {@code code id_token}, return {@code code} and {@code id_token} from the authorization endpoint
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @param issuer       The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     * @see <a href="https://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#Combinations">Definitions of Multiple-Valued Response Type Combinations</a>
     */
    public String generateCodeIdTokenAuthorizationResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail, String issuer) {
        String params = "&id_token=" + TokenUtil.createIdToken(clientDetail, userInfo, param.getNonce(), issuer);
        return this.generateAuthorizationCodeResponse(userInfo, param, clientDetail) + params;
    }

    /**
     * When the value of {@code response_type} is {@code id_token}, an {@code id_token} is returned from the authorization endpoint.
     * This mode does not require the use of token endpoints.
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @param issuer       The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     */
    public String generateIdTokenAuthorizationResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail, String issuer) {
        String params = "?id_token=" + TokenUtil.createIdToken(clientDetail, userInfo, param, issuer);
        return param.getRedirectUri() + params;
    }

    /**
     * When the value of {@code response_type} is {@code id_token token}, the {@code id_token} and {@code access_token} are returned from the authorization endpoint.
     * This mode does not require the use of token endpoints.
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @param issuer       The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     * @see <a href="https://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#Combinations">Definitions of Multiple-Valued Response Type Combinations</a>
     */
    public String generateIdTokenTokenAuthorizationResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail, String issuer) {
        AccessToken accessToken = TokenUtil.createAccessToken(userInfo, clientDetail, param.getGrantType(), param.getScope(), param.getNonce(), issuer);
        String params = "?access_token=" + accessToken.getAccessToken() + "&id_token=" + TokenUtil.createIdToken(clientDetail, userInfo, param.getNonce(), issuer);
        return param.getRedirectUri() + params;
    }

    /**
     * When the value of {@code response_type} is {@code code token}, return {@code code} and {@code token} from the authorization endpoint
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @param issuer       The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     * @see <a href="https://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#Combinations">Definitions of Multiple-Valued Response Type Combinations</a>
     */
    public String generateCodeTokenAuthorizationResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail, String issuer) {
        AccessToken accessToken = TokenUtil.createAccessToken(userInfo, clientDetail, param.getGrantType(), param.getScope(), param.getNonce(), issuer);
        String params = "&access_token=" + accessToken.getAccessToken();
        return this.generateAuthorizationCodeResponse(userInfo, param, clientDetail) + params;
    }

    /**
     * When the value of {@code response_type} is {@code code id_token token}, return {@code code},{@code id_token} and {@code token} from the authorization endpoint
     *
     * @param userInfo     Logged-in user information
     * @param param        Request parameter
     * @param clientDetail Application information
     * @param issuer       The issuer name. This parameter cannot contain the colon (:) character.
     * @return String
     * @see <a href="https://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#Combinations">Definitions of Multiple-Valued Response Type Combinations</a>
     */
    public String generateCodeIdTokenTokenAuthorizationResponse(UserInfo userInfo, IdsRequestParam param, ClientDetail clientDetail, String issuer) {
        String params = "&id_token=" + TokenUtil.createIdToken(clientDetail, userInfo, param.getNonce(), issuer);
        return this.generateCodeTokenAuthorizationResponse(userInfo, param, clientDetail, issuer) + params;
    }

    /**
     * When the value of {@code response_type} is {@code none}, the {@code code},{@code id_token} and {@code token} is not returned from the authorization endpoint,
     * if there is a state, it is returned as it is.
     *
     * @param param Request parameter
     * @return String
     * @see <a href="https://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#none">None Response Type</a>
     */
    public String generateNoneAuthorizationResponse(IdsRequestParam param) {
        String params = "";
        if (!StringUtil.isEmpty(param.getState())) {
            params = "?state=" + param.getState();
        }
        return param.getRedirectUri() + params;
    }
}
