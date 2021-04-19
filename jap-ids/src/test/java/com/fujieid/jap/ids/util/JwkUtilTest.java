package com.fujieid.jap.ids.util;

import com.fujieid.jap.ids.exception.InvalidJwksException;
import com.fujieid.jap.ids.model.enums.TokenSigningAlg;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.junit.Assert;
import org.junit.Test;

public class JwkUtilTest {

    @Test
    public void createRsaJsonWebKey() {
        RsaJsonWebKey rsaJsonWebKey = JwkUtil.createRsaJsonWebKey("jap-jwks-keyid", TokenSigningAlg.RS256);
        Assert.assertNotNull(rsaJsonWebKey);
    }

    @Test
    public void createRsaJsonWebKeyNotRsa() {
        Assert.assertThrows(InvalidJwksException.class, () -> JwkUtil.createRsaJsonWebKey("jap-jwks-keyid", TokenSigningAlg.ES256));
    }

    @Test
    public void createRsaJsonWebKeyJson() {
        String jwkJson = JwkUtil.createRsaJsonWebKeyJson("jap-jwks-keyid", TokenSigningAlg.RS256);
        Assert.assertNotNull(jwkJson);
    }

    @Test
    public void createRsaJsonWebKeySetJson() {
        String jwkJson = JwkUtil.createRsaJsonWebKeySetJson("jap-jwks-keyid", TokenSigningAlg.RS256);
        Assert.assertNotNull(jwkJson);
    }

    @Test
    public void createEsJsonWebKey() {
        EllipticCurveJsonWebKey ellipticCurveJsonWebKey = JwkUtil.createEsJsonWebKey("jap-jwks-keyid", TokenSigningAlg.ES256);
        Assert.assertNotNull(ellipticCurveJsonWebKey);
    }

    @Test
    public void createEsJsonWebKeyNotEs() {
        Assert.assertThrows(InvalidJwksException.class, () -> JwkUtil.createEsJsonWebKey("jap-jwks-keyid", TokenSigningAlg.RS256));
    }

    @Test
    public void createEsJsonWebKeyJson() {
        String jwkJson = JwkUtil.createEsJsonWebKeyJson("jap-jwks-keyid", TokenSigningAlg.ES256);
        Assert.assertNotNull(jwkJson);
    }

    @Test
    public void createEsJsonWebKeySetJson() {
        String jwkJson = JwkUtil.createEsJsonWebKeySetJson("jap-jwks-keyid", TokenSigningAlg.ES256);
        Assert.assertNotNull(jwkJson);
    }
}
