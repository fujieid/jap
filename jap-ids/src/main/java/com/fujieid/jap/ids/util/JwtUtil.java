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

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.config.JwtConfig;
import com.fujieid.jap.ids.exception.IdsTokenException;
import com.fujieid.jap.ids.exception.InvalidJwksException;
import com.fujieid.jap.ids.exception.InvalidTokenException;
import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.JwtVerificationType;
import com.fujieid.jap.ids.model.enums.TokenSigningAlg;
import com.xkcoding.json.JsonUtil;
import com.xkcoding.json.util.StringUtil;
import org.jose4j.jwk.*;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.EcKeyUtil;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.jose4j.lang.JoseException;

import java.util.Map;

/**
 * simple JSON Web Key generator：https://mkjwk.org/?spm=a2c4g.11186623.2.33.4b2040ecxvsKD7
 * <p>
 * JWT Decoder: http://jwt.calebb.net/
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JwtUtil {

    private static final Log log = LogFactory.get();

    /**
     * https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
     *
     * @param clientId      Client Identifier
     * @param userinfo      User Profile
     * @param tokenExpireIn Id Token validity (seconds)
     * @param nonce         noncestr
     * @param idsConfig     ids config
     * @return jwt token
     */
    public static String createJwtToken(String clientId, UserInfo userinfo, Long tokenExpireIn, String nonce, IdsConfig idsConfig) {
        JwtClaims claims = new JwtClaims();

        // required
        // A unique identity of the person providing the authentication information. Usually an HTTPS URL (excl. queryString and Fragment)
        claims.setIssuer(idsConfig.getIssuer());
        // The LOGO of EU provided by ISS is unique within the scope of ISS. It is used by the RP to identify a unique user. The maximum length is 255 ASCII characters
        claims.setSubject(null == userinfo ? clientId : userinfo.getId());
        // Identify the audience for ID Token. OAuth2's client_ID must be included
        claims.setAudience(clientId);
        // Expiration time. ID Token beyond this time will become invalid and will no longer be authenticated
        claims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() + (tokenExpireIn * 1000)));
        // JWT build time
        claims.setIssuedAt(NumericDate.fromMilliseconds(System.currentTimeMillis()));

        // optional
        // The random string provided by the RP when it sends a request is used to mitigate replay attacks, and the ID Token can also be associated with the RP's own Session
        if (!StringUtil.isEmpty(nonce)) {
            claims.setStringClaim(IdsConsts.NONCE, nonce);
        }
        if (null != userinfo) {
            // Time of completion of EU certification. This Claim is required if the RP carries the max_AGE parameter when sending the AuthN request
//        claims.setClaim("auth_time", "auth_time");
            // If you include other claim reference: https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims
            claims.setStringClaim("username", userinfo.getUsername());
        }

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        JwtConfig jwtConfig = idsConfig.getJwtConfig();
        PublicJsonWebKey publicJsonWebKey = IdsVerificationKeyResolver.createPublicJsonWebKey(jwtConfig.getJwksKeyId(), jwtConfig.getJwksJson(), jwtConfig.getTokenSigningAlg());
        if (null == publicJsonWebKey) {
            throw new InvalidJwksException("Unable to create Jwt Token: Unable to create public json web key.");
        }
        // The JWT is signed using the private key
        jws.setKey(publicJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(publicJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(jwtConfig.getTokenSigningAlg().getAlg());

        String idToken = null;

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        try {
            idToken = jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new IdsTokenException("Unable to create Jwt Token: " + e.getMessage());
        }

        return idToken;
    }

    public static Map<String, Object> parseJwtToken(String jwtToken, IdsConfig idsConfig) {
        JwtConfig jwtConfig = idsConfig.getJwtConfig();

        PublicJsonWebKey publicJsonWebKey = IdsVerificationKeyResolver.createPublicJsonWebKey(jwtConfig.getJwksKeyId(), jwtConfig.getJwksJson(), jwtConfig.getTokenSigningAlg());
        if (null == publicJsonWebKey) {
            throw new InvalidJwksException("Unable to parse Jwt Token: Unable to create public json web key.");
        }
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
            .setSkipDefaultAudienceValidation()
            // whom the JWT needs to have been issued by
            // allow some leeway in validating time based claims to account for clock skew
            .setAllowedClockSkewInSeconds(30)
            // verify the signature with the public key
            .setVerificationKey(publicJsonWebKey.getPublicKey())
            // create the JwtConsumer instance
            .build();

        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
            return jwtClaims.getClaimsMap();

        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            log.error("Invalid Jwt Token : " + JsonUtil.toJsonString(e.getErrorDetails()), e);
            if (e.hasExpired()) {
                throw new InvalidTokenException(ErrorResponse.EXPIRED_TOKEN);
            }
            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }
    }

    public static Map<String, Object> validateJwtToken(String clientId, String userId, String jwtToken, IdsConfig idsConfig) {
        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // and audience that identifies your system as the intended recipient.
        // If the JWT is encrypted too, you need only provide a decryption key or
        // decryption key resolver to the builder.
        JwtConfig jwtConfig = idsConfig.getJwtConfig();
        JwtConsumerBuilder jwtConsumerBuilder = new JwtConsumerBuilder();

        JwtVerificationType jwtVerificationType = jwtConfig.getJwtVerificationType();
        if (null != jwtVerificationType) {
            if (jwtVerificationType == JwtVerificationType.HTTPS_JWKS_ENDPOINT) {
                // The HttpsJwks retrieves and caches keys from a the given HTTPS JWKS endpoint.
                // Because it retains the JWKs after fetching them, it can and should be reused
                // to improve efficiency by reducing the number of outbound calls the the endpoint.
                VerificationKeyResolver verificationKeyResolver = new HttpsJwksVerificationKeyResolver(new HttpsJwks(idsConfig.getJwksUrl()));
                jwtConsumerBuilder.setVerificationKeyResolver(verificationKeyResolver);
            } else if (jwtVerificationType == JwtVerificationType.JWKS) {
                // There's also a key resolver that selects from among a given list of JWKs using the Key ID
                // and other factors provided in the header of the JWS/JWT.
                JsonWebKeySet jsonWebKeySet = IdsVerificationKeyResolver.createJsonWebKeySet(jwtConfig.getJwksJson());
                JwksVerificationKeyResolver jwksResolver = new JwksVerificationKeyResolver(jsonWebKeySet.getJsonWebKeys());
                jwtConsumerBuilder.setVerificationKeyResolver(jwksResolver);
            }
        }

        PublicJsonWebKey publicJsonWebKey = IdsVerificationKeyResolver.createPublicJsonWebKey(jwtConfig.getJwksKeyId(), jwtConfig.getJwksJson(), jwtConfig.getTokenSigningAlg());
        if (null == publicJsonWebKey) {
            throw new InvalidJwksException("Unable to verify Jwt Token: Unable to create public json web key.");
        }
        JwtConsumer jwtConsumer = jwtConsumerBuilder
            .setRequireIssuedAt()
            // the JWT must have an expiration time
            .setRequireExpirationTime()
            // the JWT must have a subject claim
            .setRequireSubject()
            // whom the JWT needs to have been issued by
            .setExpectedIssuer(idsConfig.getIssuer())
            .setExpectedSubject(StringUtil.isEmpty(userId) ? clientId : userId)
            // to whom the JWT is intended for
            .setExpectedAudience(clientId)
            // allow some leeway in validating time based claims to account for clock skew
            .setAllowedClockSkewInSeconds(30)
            // verify the signature with the public key
            .setVerificationKey(publicJsonWebKey.getPublicKey())
            // create the JwtConsumer instance
            .build();

        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
            return jwtClaims.getClaimsMap();

        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            log.error("Invalid Jwt Token! ", e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether or not the JWT has expired being one common reason for invalidity
            if (e.hasExpired()) {
                try {
                    log.error("Jwt Token expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
                } catch (MalformedClaimException malformedClaimException) {
                    malformedClaimException.printStackTrace();
                }
                throw new InvalidTokenException(ErrorResponse.EXPIRED_TOKEN);
            }

            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }
    }

    public static class IdsVerificationKeyResolver {

        public static JsonWebKeySet createJsonWebKeySet(String jwksJson) {
            InvalidJwksException invalidJwksException = new InvalidJwksException(ErrorResponse.INVALID_JWKS);
            if (StringUtil.isEmpty(jwksJson)) {
                throw invalidJwksException;
            }

            JsonWebKeySet jsonWebKeySet = null;
            try {
                jsonWebKeySet = new JsonWebKeySet(jwksJson);
            } catch (JoseException e) {
                throw invalidJwksException;
            }
            return jsonWebKeySet;
        }

        public static PublicJsonWebKey createPublicJsonWebKey(String keyId, String jwksJson, TokenSigningAlg tokenSigningAlg) {
            tokenSigningAlg = null == tokenSigningAlg ? TokenSigningAlg.RS256 : tokenSigningAlg;
            JsonWebKeySet jsonWebKeySet = createJsonWebKeySet(jwksJson);

            switch (tokenSigningAlg.getKeyType()) {
                case RsaKeyUtil.RSA:
                    return (RsaJsonWebKey) jsonWebKeySet.findJsonWebKey(keyId, tokenSigningAlg.getKeyType(), Use.SIGNATURE, tokenSigningAlg.getAlg());
                case EcKeyUtil.EC:
                    return (EllipticCurveJsonWebKey) jsonWebKeySet.findJsonWebKey(keyId, tokenSigningAlg.getKeyType(), Use.SIGNATURE, tokenSigningAlg.getAlg());
                default:
                    return null;
            }

        }
    }
}
