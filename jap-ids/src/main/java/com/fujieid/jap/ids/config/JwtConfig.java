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
package com.fujieid.jap.ids.config;

import com.fujieid.jap.ids.model.enums.JwtVerificationType;
import com.fujieid.jap.ids.model.enums.TokenSigningAlg;

/**
 * Generate/verify the global configuration of jwt token.
 * If the caller needs to configure a set of jwt config for each client,
 * you can specify jwt config when obtaining the token.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JwtConfig {

    /**
     * rsa encryption key id
     */
    private String jwksKeyId = "jap-jwk-keyid";
    /**
     * <strong>Optional</strong>, jwt token verification type, the default is {@code JwtVerificationType.HTTPS_JWKS_ENDPOINT}
     * <p>
     * The usage is as follows:
     * <p>
     * 1. If the public key of the jwt issuer is at the https jwks endpoint, please set it to {@code JwtVerificationType.HTTPS_JWKS_ENDPOINT}
     * <p>
     * 2. When using jwks certificate string verification, please set it to {@code JwtVerificationType.JWKS}
     * <p>
     * 3. When using x.509 certificate string verification, please set it to {@code JwtVerificationType.X_509}
     */
    private JwtVerificationType jwtVerificationType = JwtVerificationType.HTTPS_JWKS_ENDPOINT;
    /**
     * <strong>Optional</strong>, when {@link JwtConfig#jwtVerificationType} is equal to {@code JWKS}, this attribute is <strong>required</strong>
     */
    private String jwksJson;
    /**
     * jwt token encryption algorithm, the default is {@code RS256}
     */
    private TokenSigningAlg tokenSigningAlg = TokenSigningAlg.RS256;

    public JwtVerificationType getJwtVerificationType() {
        return jwtVerificationType;
    }

    public JwtConfig setJwtVerificationType(JwtVerificationType jwtVerificationType) {
        this.jwtVerificationType = jwtVerificationType;
        return this;
    }

    public String getJwksKeyId() {
        return jwksKeyId;
    }

    public JwtConfig setJwksKeyId(String jwksKeyId) {
        this.jwksKeyId = jwksKeyId;
        return this;
    }

    public String getJwksJson() {
        return jwksJson;
    }

    public JwtConfig setJwksJson(String jwksJson) {
        this.jwksJson = jwksJson;
        return this;
    }

    public TokenSigningAlg getTokenSigningAlg() {
        return tokenSigningAlg;
    }

    public JwtConfig setTokenSigningAlg(TokenSigningAlg tokenSigningAlg) {
        this.tokenSigningAlg = tokenSigningAlg;
        return this;
    }
}
