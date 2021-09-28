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
package com.fujieid.jap.ldap.password;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public enum PasswordAlgorithm {
    /**
     * Plaintext
     */
    CLEAR(""),
    /**
     * {K5KEY}123456
     */
    K5KEY(""),
    /**
     * md5:    {MD5}4QrcOUm6Wau+VuBX8g+IPg==
     */
    MD5("MD5"),
    /**
     * {SMD5}qx2PeUUZwWTldNSFJWav0+H2c68=
     */
    SMD5(""),
    /**
     * {SHA}fEqNCco3Yq9h5ZUglD3CZJT4lBs=
     */
    SHA("SHA-1"),
    /**
     * {SSHA}ZAMWfL5n96G2O5YlgCGGLfwp/+HOY9QA
     */
    SSHA("SHA-1"),
    /**
     * {SHA512}ujJTh2rta8ItSm/1PYQGxq2GQZXtFEq1yHYhtsIztUi66uaVbfNG7IwX9eoQ817jy8UUeX7X3dMUVGTioLq0Ew==
     */
    SHA512("SHA-512"),
    SHA256("SHA-256"),
    /**
     * ext_des:   {CRYPT}_uiCfk84UD.Kappl.tJU
     */
    EXT_DES(""),
    /**
     * md5crypt:    {CRYPT}$1$iUO9tzUE$7ixRoo26S/4ZoJywINpKo0
     */
    MD5CRYPT(""),
    /**
     * sha256crypt:   {CRYPT}$5$YuXmJhhI$SylTrOuTBXAWC.PmJfaj5kGQtwkbUEsLwK.c8k0mpM2
     */
    SHA256CRYPT(""),
    /**
     * sha512crypt:   {CRYPT}$6$nNLKOfFc$XA9TM0MJtSrCCkZYTkaKlRYZ/mvLehnZ9ovX0WHaqPgMIuiMQxcgUKpp6ZVM2Kuqqk1e2UZUsKX4VJ0YAPmgx1
     */
    SHA512CRYPT(""),
    /**
     * crypt:   {CRYPT}b785FaYHDFNCI
     */
    CRYPT(""),
    ;

    private final String algorithm;

    PasswordAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
