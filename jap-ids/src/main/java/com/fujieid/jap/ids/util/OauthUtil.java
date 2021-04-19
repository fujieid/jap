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

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import com.fujieid.jap.ids.exception.*;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.service.Oauth2Service;
import com.xkcoding.json.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class OauthUtil {
    private static final Collection<String> REDIRECT_GRANT_TYPES = Arrays.asList("implicit", "authorization_code");

    /**
     * Convert string to list
     *
     * @param text       The string to be converted
     * @param splitRegex Regular expression to split string
     * @return List of strings (de-duplicated)
     */
    public static Set<String> convertStrToList(String text, String splitRegex) {
        Set<String> result = new TreeSet<>();
        if (text != null && text.trim().length() > 0) {
            String[] tokens = text.split(splitRegex);
            result.addAll(Arrays.asList(tokens));
        }
        return result;
    }

    /**
     * Convert string to list
     *
     * @param text The string to be converted
     * @return List of strings (de-duplicated)
     */
    public static Set<String> convertStrToList(String text) {
        return convertStrToList(text, "[\\s+]");
    }

    /**
     * @param requestScopes The scope parameter in the current request
     * @param clientScopes  Scope in client detail
     * @return After the verification is passed, return the scope list
     */
    public static Set<String> validateScope(String requestScopes, String clientScopes) {

        if (StringUtil.isEmpty(requestScopes)) {
            throw new InvalidScopeException("Empty scope (either the client or the user is not allowed the requested scopes)");
        }
        Set<String> scopes = OauthUtil.convertStrToList(requestScopes);

        if (StringUtil.isNotEmpty(clientScopes)) {
            Set<String> appScopes = OauthUtil.convertStrToList(clientScopes);
            for (String scope : scopes) {
                if (!appScopes.contains(scope)) {
                    throw new InvalidScopeException("Invalid scope: " + scope + ". " + clientScopes);
                }
            }
        }

        return scopes;
    }


    /**
     * Determine whether the current grant type supports redirect uri
     *
     * @param grantTypes some grant types
     * @return true if the supplied grant types includes one or more of the redirect types
     */
    private static boolean containsRedirectGrantType(Set<String> grantTypes) {
        for (String type : grantTypes) {
            if (REDIRECT_GRANT_TYPES.contains(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify the callback url
     *
     * @param requestRedirectUri The callback url passed in the current request
     * @param clientDetail       client detail
     */
    public static void validateRedirectUri(String requestRedirectUri, ClientDetail clientDetail) {
        String clientGrantTypes = clientDetail.getGrantTypes();
        Set<String> clientGrantTypeSet = OauthUtil.convertStrToList(clientGrantTypes);
        if (clientGrantTypeSet.isEmpty()) {
            throw new InvalidGrantException("A client must have at least one authorized grant type.");
        }
        if (!containsRedirectGrantType(clientGrantTypeSet)) {
            throw new InvalidGrantException(
                "A redirect_uri can only be used by implicit or authorization_code grant types.");
        }
        String clientRedirectUri = clientDetail.getRedirectUri();
        if (requestRedirectUri == null || !requestRedirectUri.equals(clientRedirectUri)) {
            throw new InvalidRedirectUriException(ErrorResponse.INVALID_REDIRECT_URI);
        }
    }

    /**
     * 1. Only the authorization code mode will verify pkce, and the client secret will be verified when pkce is not enabled
     * <p>
     * 2. Implicit authorization grant and password authorization grant do not need to verify client secret
     * <p>
     * 3. Client authorizatio grantn needs to verify the client secret
     *
     * @param param         request params
     * @param clientDetail  client detail
     * @param oauth2Service oauth2Service
     */
    public static void validateSecret(IdsRequestParam param, ClientDetail clientDetail, Oauth2Service oauth2Service) {
        if (param.getGrantType().equals(GrantType.AUTHORIZATION_CODE.getType())) {
            if (param.isEnablePkce()) {
                oauth2Service.validateAuthrizationCodeChallenge(param.getCodeVerifier(), param.getCode());
            } else {
                if (StringUtil.isEmpty(param.getClientSecret()) || !clientDetail.getClientSecret().equals(param.getClientSecret())) {
                    throw new InvalidClientException(ErrorResponse.INVALID_CLIENT);
                }
            }
        } else {
            if (StringUtil.isEmpty(param.getClientSecret()) || !clientDetail.getClientSecret().equals(param.getClientSecret())) {
                throw new InvalidClientException(ErrorResponse.INVALID_CLIENT);
            }
        }
    }

    /**
     * Verify the response type
     *
     * @param requestResponseType The response type in the current request
     * @param clientResponseTypes Response type in client detail
     */
    public static void validateResponseType(String requestResponseType, String clientResponseTypes) {
        Set<String> clientResponseTypeSet = OauthUtil.convertStrToList(clientResponseTypes);
        if (!StringUtil.isEmpty(clientResponseTypes) && !clientResponseTypeSet.contains(requestResponseType)) {
            throw new UnsupportedResponseTypeException(ErrorResponse.UNSUPPORTED_RESPONSE_TYPE);
        }
    }

    /**
     * Verify the grant type
     *
     * @param requestGrantType The grant type in the current request
     * @param clientGrantTypes Grant type in client detail
     * @param equalTo          {@code requestGrantType} Must match grant type value
     */
    public static void validateGrantType(String requestGrantType, String clientGrantTypes, GrantType equalTo) {
        Set<String> grantTypeSet = OauthUtil.convertStrToList(clientGrantTypes);
        if (StringUtil.isEmpty(requestGrantType) || ArrayUtil.isEmpty(grantTypeSet) || !grantTypeSet.contains(requestGrantType)) {
            throw new UnsupportedGrantTypeException(ErrorResponse.UNSUPPORTED_GRANT_TYPE);
        }
        if (null != equalTo && !requestGrantType.equals(equalTo.getType())) {
            throw new UnsupportedGrantTypeException(ErrorResponse.UNSUPPORTED_GRANT_TYPE);
        }
    }

    public static void validClientDetail(ClientDetail clientDetail) {
        if (clientDetail == null) {
            throw new InvalidClientException(ErrorResponse.INVALID_CLIENT);
        }
        if (!Optional.ofNullable(clientDetail.getAvailable()).orElse(false)) {
            throw new InvalidClientException(ErrorResponse.DISABLED_CLIENT);
        }
    }

    /**
     * Get the expiration time of access token, default is {@link IdsConsts#ACCESS_TOKEN_ACTIVITY_TIME}
     *
     * @param expiresIn The expiration time of the access token in the client detail
     * @return long
     */
    public static long getAccessTokenExpiresIn(Long expiresIn) {
        return Optional.ofNullable(expiresIn).orElse(IdsConsts.ACCESS_TOKEN_ACTIVITY_TIME);
    }

    /**
     * Get the expiration time of refresh token, the default is {@link IdsConsts#REFRESH_TOKEN_ACTIVITY_TIME}
     *
     * @param expiresIn The expiration time of the refresh token in the client detail
     * @return long
     */
    public static long getRefreshTokenExpiresIn(Long expiresIn) {
        return Optional.ofNullable(expiresIn).orElse(IdsConsts.REFRESH_TOKEN_ACTIVITY_TIME);
    }

    /**
     * Get the expiration time of the authorization code code, the default is {@link IdsConsts#AUTHORIZATION_CODE_ACTIVITY_TIME}
     *
     * @param expiresIn The expiration time of the code in the client detail
     * @return long
     */
    public static long getCodeExpiresIn(Long expiresIn) {
        return Optional.ofNullable(expiresIn).orElse(IdsConsts.AUTHORIZATION_CODE_ACTIVITY_TIME);
    }

    /**
     * Get the expiration time of id token, the default is{@link IdsConsts#ID_TOKEN_ACTIVITY_TIME}
     *
     * @param expiresIn The expiration time of the id token in the client detail
     * @return long
     */
    public static long getIdTokenExpiresIn(Long expiresIn) {
        return Optional.ofNullable(expiresIn).orElse(IdsConsts.ID_TOKEN_ACTIVITY_TIME);
    }

    /**
     * Get the expiration deadline of access token
     *
     * @param expiresIn The expiration time of the access token in the client detail
     * @return long
     */
    public static LocalDateTime getAccessTokenExpiresAt(Long expiresIn) {
        expiresIn = getAccessTokenExpiresIn(expiresIn);
        return DateUtil.ofEpochSecond(System.currentTimeMillis() + expiresIn * 1000, null);
    }

    /**
     * Get the expiration deadline of refresh token
     *
     * @param expiresIn The expiration time of the refresh token in the client detail
     * @return long
     */
    public static LocalDateTime getRefreshTokenExpiresAt(Long expiresIn) {
        expiresIn = getRefreshTokenExpiresIn(expiresIn);
        return DateUtil.ofEpochSecond(System.currentTimeMillis() + expiresIn * 1000, null);
    }

    /**
     * Get the expiration deadline of authorization code
     *
     * @param expiresIn The expiration time of the code in the client detail
     * @return long
     */
    public static LocalDateTime getCodeExpiresAt(Long expiresIn) {
        expiresIn = getCodeExpiresIn(expiresIn);
        return DateUtil.ofEpochSecond(System.currentTimeMillis() + expiresIn * 1000, null);
    }

    /**
     * Get the expiration deadline of id token
     *
     * @param expiresIn The expiration time of the id token in the client detail
     * @return long
     */
    public static LocalDateTime getIdTokenExpiresAt(Long expiresIn) {
        expiresIn = getIdTokenExpiresIn(expiresIn);
        return DateUtil.ofEpochSecond(System.currentTimeMillis() + expiresIn * 1000, null);
    }

    /**
     * Create authorize url
     *
     * @param authorizeUrl authorize url
     * @param param        request params
     * @return authorizeUrl
     */
    public static String createAuthorizeUrl(String authorizeUrl, IdsRequestParam param) {
        Map<String, Object> model = new HashMap<>(13);
        model.put("client_id", param.getClientId());

        if (StringUtil.isNotEmpty(param.getRedirectUri())) {
            model.put("redirect_uri", param.getRedirectUri());
        }

        if (StringUtil.isNotEmpty(param.getScope())) {
            model.put("scope", param.getScope());
        }

        if (StringUtil.isNotEmpty(param.getState())) {
            model.put("state", param.getState());
        }

        if (StringUtil.isNotEmpty(param.getNonce())) {
            model.put("nonce", param.getNonce());
        }

        if (StringUtil.isNotEmpty(param.getResponseType())) {
            model.put("response_type", param.getResponseType());
        }
        if (StringUtil.isNotEmpty(param.getCodeChallengeMethod()) || StringUtil.isNotEmpty(param.getCodeChallenge())) {
            model.put("code_challenge_method", param.getCodeChallengeMethod());
            model.put("code_challenge", param.getCodeChallenge());
        }
        if (StringUtil.isNotEmpty(param.getAutoapprove())) {
            model.put("autoapprove", param.getAutoapprove());
        }
        String uriParams = URLUtil.buildQuery(model, StandardCharsets.UTF_8);
        if (authorizeUrl.contains("?")) {
            authorizeUrl = authorizeUrl + (authorizeUrl.endsWith("?") ? "" : "&") + uriParams;
        } else {
            authorizeUrl = authorizeUrl + "?" + uriParams;
        }
        return authorizeUrl;
    }

    public static String generateClientId() {
        return RandomUtil.randomString(32);
    }

    public static String generateClientSecret() {
        return RandomUtil.randomString(40);
    }

    public static boolean isOidcProtocol(String scopes) {
        Set<String> scopeList = OauthUtil.convertStrToList(scopes);
        return scopeList.contains("openid");
    }

    /**
     * Suitable for oauth 2.0 pkce enhanced protocol
     *
     * @param codeChallengeMethod s256 / plain
     * @param codeVerifier        code verifier, Generated by the developer
     * @return code challenge
     */
    public static String generateCodeChallenge(String codeChallengeMethod, String codeVerifier) {
        if ("S256".equalsIgnoreCase(codeChallengeMethod)) {
            // https://tools.ietf.org/html/rfc7636#section-4.2
            // code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
            return Base64.encodeUrlSafe(SecureUtil.sha256().digest(codeVerifier));
        } else {
            return codeVerifier;
        }
    }

}
