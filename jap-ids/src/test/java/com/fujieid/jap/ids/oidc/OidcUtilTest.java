package com.fujieid.jap.ids.oidc;

import com.fujieid.jap.ids.BaseIdsTest;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.InvalidJwksException;
import com.fujieid.jap.ids.model.OidcDiscoveryDto;
import com.xkcoding.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;

public class OidcUtilTest extends BaseIdsTest {

    @Test
    public void getOidcDiscoveryInfoEqual() {
        OidcDiscoveryDto discoveryDto = OidcUtil.getOidcDiscoveryInfo(null);
//        System.out.println(JsonUtil.toJsonString(discoveryDto));
        String json = JsonUtil.toJsonString(discoveryDto);
//        Assert.assertEquals("{\"issuer\":\"http://www.baidu.com\",\"authorization_endpoint\":\"http://www.baidu.com/oauth/authorize\",\"token_endpoint\":\"http://www.baidu.com/oauth/token\",\"userinfo_endpoint\":\"http://www.baidu.com/oauth/userinfo\",\"registration_endpoint\":\"http://www.baidu.com/oauth/registration\",\"end_session_endpoint\":\"http://www.baidu.com/oauth/logout\",\"check_session_iframe\":\"http://www.baidu.com/oauth/check_session\",\"jwks_uri\":\"http://www.baidu.com/.well-known/jwks.json\",\"grant_types_supported\":[\"authorization_code\",\"implicit\",\"password\",\"client_credentials\",\"refresh_token\",\"token\"],\"response_modes_supported\":[\"fragment\",\"query\"],\"response_types_supported\":[\"code\",\"token\",\"id_token\",\"id_token token\",\"code id_token\",\"code token\",\"code id_token token\",\"none\"],\"scopes_supported\":[\"read\",\"write\",\"openid\",\"profile\",\"email\",\"phone\",\"address\"],\"token_endpoint_auth_methods_supported\":[\"client_secret_post\",\"client_secret_basic\",\"none\"],\"request_object_signing_alg_values_supported\":[\"none\",\"RS256\",\"ES256\"],\"userinfo_signing_alg_values_supported\":[\"RS256\",\"ES256\"],\"request_parameter_supported\":true,\"request_uri_parameter_supported\":true,\"require_request_uri_registration\":false,\"claims_parameter_supported\":true,\"id_token_signing_alg_values_supported\":[\"RS256\",\"ES256\"],\"subject_types_supported\":[\"public\"],\"claims_supported\":[\"iss\",\"sub\",\"aud\",\"exp\",\"iat\",\"nonce\",\"auth_time\",\"username\"]}", json);
        Assert.assertNotNull(json);
    }

    @Test
    public void getJwksPublicKey() {
        String jwks = OidcUtil.getJwksPublicKey(null);
        Assert.assertEquals("{\"keys\":[{\"kty\":\"RSA\",\"kid\":\"jap-jwk-keyid\",\"use\":\"sig\",\"alg\":\"RS256\",\"n\":\"hj8zFdhYFi-47PO4B4HTRuOLPR_rpZJi66g4JoY4gyhb5v3Q57etSU9BnW9QQNoUMDvhCFSwkz0hgY5HqVj0zOG5s9x2a594UDIinKsm434b-pT6bueYdvM_mIUEKka5pqhy90wTTka42GvM-rBATHPTarq0kPTR1iBtYao8zX-RWmCbdumEWOkMFUGbBkUcOSJWzoLzN161WdYr2kJU5PFraUP3hG9fPpMEtvqd6IwEL-MOVx3nqc7zk3D91E6eU7EaOy8nz8echQLl6Ps34BSwEpgOhaHDD6IJzetW-KorYeC0r0okXhrl0sUVE2c71vKPVVtueJSIH6OwA3dVHQ\",\"e\":\"AQAB\"}]}", jwks);
    }

    @Test
    public void getJwksPublicKeyNullJwksJson() {
        JapIds.getIdsConfig().getJwtConfig().setJwksJson(null);
        Assert.assertThrows(InvalidJwksException.class, () -> OidcUtil.getJwksPublicKey(null));
    }
}
