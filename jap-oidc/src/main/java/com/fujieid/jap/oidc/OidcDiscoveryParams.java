package com.fujieid.jap.oidc;

/**
 * Property name of IDP service discovery
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 17:12
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
