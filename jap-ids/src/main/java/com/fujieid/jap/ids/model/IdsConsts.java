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

/**
 * ids constant
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IdsConsts {
    String SLASH = "/";

    /**
     * The default validity period of the authorization code is 10 minutes (600 seconds)
     */
    long AUTHORIZATION_CODE_ACTIVITY_TIME = 600;
    /**
     * The default validity period of access token is 30 days (2592000 seconds)
     */
    long ACCESS_TOKEN_ACTIVITY_TIME = 2592000;
    /**
     * The default validity period of refresh token is 365 days (31536000 seconds)
     */
    long REFRESH_TOKEN_ACTIVITY_TIME = 31536000;
    /**
     * The default validity period of id token is 365 days (31536000 seconds)
     */
    long ID_TOKEN_ACTIVITY_TIME = 31536000;

    /**
     * token header name
     */
    String AUTHORIZATION_HEADER_NAME = "Authorization";

    /**
     * Token Type
     */
    String TOKEN_TYPE_BEARER = "Bearer";

    /**
     * Cache key of oauth authorized user
     */
    String IDS_OAUTH_CACHE_KEY = "JAPIDS:OAUTH2:";

    /**
     * Cache key of oauth authorized user
     */
    String OAUTH_USERINFO_CACHE_KEY = IDS_OAUTH_CACHE_KEY + "USERINFO";

    /**
     * Cache the key of access token
     */
    String OAUTH_ACCESS_TOKEN_CACHE_KEY = IDS_OAUTH_CACHE_KEY + "ACCESS_TOKEN:";

    /**
     * Cache the key of refresh token
     */
    String OAUTH_REFRESH_TOKEN_CACHE_KEY = IDS_OAUTH_CACHE_KEY + "REFRESH_TOKEN:";

    /**
     * Cache the key of the oauth code
     */
    String OAUTH_CODE_CACHE_KEY = IDS_OAUTH_CACHE_KEY + "CODE:";

    String CODE_CHALLENGE = "code_challenge";
    String CODE_CHALLENGE_METHOD = "code_challenge_method";
    String CODE_VERIFIER = "code_verifier";
    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";
    String SCOPE = "scope";
    String REDIRECT_URI = "redirect_uri";
    String STATE = "state";
    String RESPONSE_TYPE = "response_type";
    String GRANT_TYPE = "grant_type";
    String TOKEN_TYPE = "token_type";
    String ACCESS_TOKEN = "access_token";
    String REFRESH_TOKEN = "refresh_token";
    String ID_TOKEN = "id_token";
    String EXPIRES_IN = "expires_in";
    String UID = "uid";
    String CODE = "code";
    String RESPONSE_MODE = "response_mode";
    String DISPLAY = "display";
    String PROMPT = "prompt";
    String MAX_AGE = "max_age";
    String ID_TOKEN_HINT = "id_token_hint";
    String AUTOAPPROVE = "autoapprove";
    String USERNAME = "username";
    String PASSWORD = "password";
    /**
     * {@code auth_time} - the time when the End-User authentication occurred
     */
    String AUTH_TIME = "auth_time";

    /**
     * {@code nonce} - a {@code String} value used to associate a Client session with an ID Token,
     * and to mitigate replay attacks.
     */
    String NONCE = "nonce";

    /**
     * {@code acr} - the Authentication Context Class Reference
     */
    String ACR = "acr";
}
