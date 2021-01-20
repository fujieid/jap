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

import java.io.Serializable;

/**
 * OpenID Provider Issuer discovery is the process of determining the location of the OpenID Provider.
 * <p>
 * For the properties defined by this class, please refer to [3.  OpenID Provider Metadata]
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2020/10/26 14:47
 * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html" target="_blank">OpenID Connect Discovery 1.0 incorporating errata set 1</a>
 * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata" target="_blank">3.  OpenID Provider Metadata</a>
 * @since 1.0.0
 */
public class OidcDiscoveryDto implements Serializable {

    /**
     * Identity provider URL
     */
    private String issuer;
    /**
     * URL of the OP's OAuth 2.0 Authorization Endpoint
     */
    private String authorizationEndpoint;
    /**
     * URL of the OP's OAuth 2.0 Token Endpoint
     */
    private String tokenEndpoint;
    /**
     * URL of the OP's UserInfo Endpoint
     */
    private String userinfoEndpoint;
    /**
     * URL of the OP's Logout Endpoint
     */
    private String endSessionEndpoint;
    /**
     * URL of the OP's JSON Web Key Set [JWK] document
     *
     * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#JWK" target="_blank">JWK</a>
     */
    private String jwksUri;

    public String getIssuer() {
        return issuer;
    }

    public OidcDiscoveryDto setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public OidcDiscoveryDto setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
        return this;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public OidcDiscoveryDto setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
        return this;
    }

    public String getUserinfoEndpoint() {
        return userinfoEndpoint;
    }

    public OidcDiscoveryDto setUserinfoEndpoint(String userinfoEndpoint) {
        this.userinfoEndpoint = userinfoEndpoint;
        return this;
    }

    public String getEndSessionEndpoint() {
        return endSessionEndpoint;
    }

    public OidcDiscoveryDto setEndSessionEndpoint(String endSessionEndpoint) {
        this.endSessionEndpoint = endSessionEndpoint;
        return this;
    }

    public String getJwksUri() {
        return jwksUri;
    }

    public OidcDiscoveryDto setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
        return this;
    }
}
