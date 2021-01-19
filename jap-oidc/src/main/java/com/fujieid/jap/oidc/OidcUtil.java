package com.fujieid.jap.oidc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.exception.OidcException;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2020/12/3 14:13
 * @since 1.0.0
 */
public class OidcUtil {

    private static final String DISCOVERY_URL = "/.well-known/openid-configuration";

    /**
     * Get the IDP service configuration
     *
     * @param issuer IDP identity providers, such as `https://sign.fujieid.com`
     * @return OidcDiscoveryDto
     */
    public static OidcDiscoveryDto getOidcDiscovery(String issuer) {
        if (StrUtil.isBlank(issuer)) {
            throw new OidcException("Missing IDP Discovery Url.");
        }
        String discoveryUrl = issuer.concat(DISCOVERY_URL);

        HttpResponse httpResponse = HttpRequest.get(discoveryUrl).execute();
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.body());
        if (CollectionUtil.isEmpty(jsonObject)) {
            throw new OidcException("Unable to parse IDP service discovery configuration information.");
        }
        return new OidcDiscoveryDto()
                .setIssuer(jsonObject.getString(OidcDiscoveryParams.ISSUER))
                .setAuthorizationEndpoint(jsonObject.getString(OidcDiscoveryParams.AUTHORIZATION_ENDPOINT))
                .setTokenEndpoint(jsonObject.getString(OidcDiscoveryParams.TOKEN_ENDPOINT))
                .setUserinfoEndpoint(jsonObject.getString(OidcDiscoveryParams.USERINFO_ENDPOINT))
                .setEndSessionEndpoint(jsonObject.getString(OidcDiscoveryParams.END_SESSION_ENDPOINT))
                .setJwksUri(jsonObject.getString(OidcDiscoveryParams.JWKS_URI));
    }


}
