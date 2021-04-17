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
package com.fujieid.jap.ids.oidc;

import java.io.Serializable;

/**
 * According to standard specifications, construct id token
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#IDToken" target="_blank">ID Token</a>
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims" target="_blank">Standard Claims</a>
 * @since 1.0.1
 */
public class IdToken implements Serializable {
    /**
     * {@code iss} - the Issuer identifier
     */
    private String iss;

    /**
     * {@code sub} - the Subject identifier
     */
    private String sub;

    /**
     * {@code aud} - the Audience(s) that the ID Token is intended for
     */
    private String aud;

    /**
     * {@code exp} - the Expiration time on or after which the ID Token MUST NOT be accepted
     */
    private Long exp;

    /**
     * {@code iat} - the time at which the ID Token was issued
     */
    private Long iat;

    /**
     * {@code auth_time} - the time when the End-User authentication occurred
     */
    private String auth_time;

    /**
     * {@code nonce} - a {@code String} value used to associate a Client session with an ID Token,
     * and to mitigate replay attacks.
     */
    private String nonce;

    /**
     * {@code acr} - the Authentication Context Class Reference
     */
    private String acr;

    /**
     * {@code amr} - the Authentication Methods References
     */
    private String amr;

    /**
     * {@code azp} - the Authorized party to which the ID Token was issued
     */
    private String azp;

    /**
     * {@code at_hash} - the Access Token hash value
     */
    private String at_hash;

    /**
     * {@code c_hash} - the Authorization Code hash value
     */
    private String c_hash;

    private Object extra;

    public String getIss() {
        return iss;
    }

    public IdToken setIss(String iss) {
        this.iss = iss;
        return this;
    }

    public String getSub() {
        return sub;
    }

    public IdToken setSub(String sub) {
        this.sub = sub;
        return this;
    }

    public String getAud() {
        return aud;
    }

    public IdToken setAud(String aud) {
        this.aud = aud;
        return this;
    }

    public Long getExp() {
        return exp;
    }

    public IdToken setExp(Long exp) {
        this.exp = exp;
        return this;
    }

    public Long getIat() {
        return iat;
    }

    public IdToken setIat(Long iat) {
        this.iat = iat;
        return this;
    }

    public String getAuth_time() {
        return auth_time;
    }

    public IdToken setAuth_time(String auth_time) {
        this.auth_time = auth_time;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public IdToken setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getAcr() {
        return acr;
    }

    public IdToken setAcr(String acr) {
        this.acr = acr;
        return this;
    }

    public String getAmr() {
        return amr;
    }

    public IdToken setAmr(String amr) {
        this.amr = amr;
        return this;
    }

    public String getAzp() {
        return azp;
    }

    public IdToken setAzp(String azp) {
        this.azp = azp;
        return this;
    }

    public String getAt_hash() {
        return at_hash;
    }

    public IdToken setAt_hash(String at_hash) {
        this.at_hash = at_hash;
        return this;
    }

    public String getC_hash() {
        return c_hash;
    }

    public IdToken setC_hash(String c_hash) {
        this.c_hash = c_hash;
        return this;
    }

    public Object getExtra() {
        return extra;
    }

    public IdToken setExtra(Object extra) {
        this.extra = extra;
        return this;
    }
}
