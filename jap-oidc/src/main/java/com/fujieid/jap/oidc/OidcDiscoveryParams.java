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
package com.fujieid.jap.oidc;

/**
 * Property name of IDP service discovery
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata" target="_blank">3.  OpenID Provider Metadata</a>
 * @since 1.0.0
 */
public interface OidcDiscoveryParams {

    /**
     * Identity provider URL
     */
    String ISSUER = "issuer";
    /**
     * URL of the OP's OAuth 2.0 Authorization Endpoint
     */
    String AUTHORIZATION_ENDPOINT = "authorization_endpoint";
    /**
     * URL of the OP's OAuth 2.0 Token Endpoint
     */
    String TOKEN_ENDPOINT = "token_endpoint";
    /**
     * URL of the OP's UserInfo Endpoint
     */
    String USERINFO_ENDPOINT = "userinfo_endpoint";
    /**
     * URL of the OP's Logout Endpoint
     */
    String END_SESSION_ENDPOINT = "end_session_endpoint";
    /**
     * URL of the OP's JSON Web Key Set [JWK] document
     *
     * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#JWK" target="_blank">JWK</a>
     */
    String JWKS_URI = "jwks_uri";
}
