package com.fujieid.jap.ids.oidc;

import com.fujieid.jap.ids.BaseIdsTest;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.exception.InvalidJwksException;
import org.junit.Assert;
import org.junit.Test;

public class OidcUtilTest extends BaseIdsTest {

    @Test
    public void getOidcDiscoveryInfoEqual() {
        Assert.assertThrows(IdsException.class, () -> OidcUtil.getOidcDiscoveryInfo(null));
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
