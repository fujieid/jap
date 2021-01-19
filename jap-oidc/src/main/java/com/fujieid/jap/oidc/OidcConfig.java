package com.fujieid.jap.oidc;

import com.fujieid.jap.oauth2.OAuthConfig;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 16:23
 * @since 1.0.0
 */
public class OidcConfig extends OAuthConfig {
    private String issuer;
    private String userNameAttribute;

    public String getIssuer() {
        return issuer;
    }

    public OidcConfig setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public String getUserNameAttribute() {
        return userNameAttribute;
    }

    public OidcConfig setUserNameAttribute(String userNameAttribute) {
        this.userNameAttribute = userNameAttribute;
        return this;
    }
}
