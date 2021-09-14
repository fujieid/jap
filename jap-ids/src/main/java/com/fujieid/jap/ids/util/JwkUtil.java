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

import com.fujieid.jap.ids.exception.InvalidJwksException;
import com.fujieid.jap.ids.model.enums.TokenSigningAlg;
import org.jose4j.jwk.*;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.lang.JoseException;

import java.security.spec.ECParameterSpec;
import java.util.Arrays;

/**
 * Generate json web key encryption certificate
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JwkUtil {

    /**
     * Create rsa json web key
     *
     * @param keyId      key id
     * @param signingAlg Encryption Algorithm
     * @return RsaJsonWebKey
     */
    public static RsaJsonWebKey createRsaJsonWebKey(String keyId, TokenSigningAlg signingAlg) {
        if (!Arrays.asList(TokenSigningAlg.RS256, TokenSigningAlg.RS384, TokenSigningAlg.RS512).contains(signingAlg)) {
            throw new InvalidJwksException("Unable to create RSA Json Web Key. Unsupported jwk algorithm, only supports RS256, RS384, RS512");
        }
        RsaJsonWebKey jwk = null;
        try {
            jwk = RsaJwkGenerator.generateJwk(2048);
            jwk.setUse(Use.SIGNATURE);
            jwk.setKeyId(keyId);
            jwk.setAlgorithm(signingAlg.getAlg());
        } catch (JoseException e) {
            e.printStackTrace();
            throw new InvalidJwksException("Unable to create RSA Json Web Key.");
        }
        return jwk;
    }

    /**
     * Create the json string of rsa json web key
     *
     * @param keyId      key id
     * @param signingAlg Encryption Algorithm
     * @return RSA Json Web Key Json
     */
    public static String createRsaJsonWebKeyJson(String keyId, TokenSigningAlg signingAlg) {
        RsaJsonWebKey jwk = createRsaJsonWebKey(keyId, signingAlg);
        return jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
    }

    /**
     * Create a set collection of rsa json web key json string
     *
     * @param keyId      key id
     * @param signingAlg Encryption Algorithm
     * @return RSA Json Web Key Set Json
     */
    public static String createRsaJsonWebKeySetJson(String keyId, TokenSigningAlg signingAlg) {
        RsaJsonWebKey jwk = createRsaJsonWebKey(keyId, signingAlg);
        return new JsonWebKeySet(jwk).toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
    }

    /**
     * Create es json web key
     *
     * @param keyId      key id
     * @param signingAlg Encryption Algorithm
     * @return Elliptic Curve Json Web Key
     */
    public static EllipticCurveJsonWebKey createEsJsonWebKey(String keyId, TokenSigningAlg signingAlg) {
        if (!Arrays.asList(TokenSigningAlg.ES256, TokenSigningAlg.ES384, TokenSigningAlg.ES512).contains(signingAlg)) {
            throw new InvalidJwksException("Unable to create ES Json Web Key. Unsupported jwk algorithm, only supports ES256, ES384, ES512");
        }
        EllipticCurveJsonWebKey jwk = null;

        ECParameterSpec spec = null;
        if (signingAlg == TokenSigningAlg.ES256) {
            spec = EllipticCurves.P256;
        } else if (signingAlg == TokenSigningAlg.ES384) {
            spec = EllipticCurves.P384;
        } else {
            spec = EllipticCurves.P521;
        }

        try {
            jwk = EcJwkGenerator.generateJwk(spec);
            jwk.setUse(Use.SIGNATURE);
            jwk.setKeyId(keyId);
            jwk.setAlgorithm(signingAlg.getAlg());
        } catch (JoseException e) {
            e.printStackTrace();
            throw new InvalidJwksException("Unable to create ES Json Web Key.");
        }
        return jwk;
    }

    /**
     * Create json string of es json web key
     *
     * @param keyId      key id
     * @param signingAlg Encryption Algorithm
     * @return ES Json Web Key Json
     */
    public static String createEsJsonWebKeyJson(String keyId, TokenSigningAlg signingAlg) {
        EllipticCurveJsonWebKey jwk = createEsJsonWebKey(keyId, signingAlg);
        return jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
    }

    /**
     * Create a set collection of es json web key json string
     *
     * @param keyId      key id
     * @param signingAlg Encryption Algorithm
     * @return RS Json Web Key Set Json
     */
    public static String createEsJsonWebKeySetJson(String keyId, TokenSigningAlg signingAlg) {
        EllipticCurveJsonWebKey jwk = createEsJsonWebKey(keyId, signingAlg);
        return new JsonWebKeySet(jwk).toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
    }
}
