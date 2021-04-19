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
package com.fujieid.jap.ids.model.enums;

import org.jose4j.keys.EcKeyUtil;
import org.jose4j.keys.RsaKeyUtil;

/**
 * jwt token encryption algorithm, Supports two types of algorithms, RSA and EC
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum TokenSigningAlg {
    /**
     * RS256
     */
    RS256("RS256", RsaKeyUtil.RSA),
    RS384("RS384", RsaKeyUtil.RSA),
    RS512("RS512", RsaKeyUtil.RSA),
    ES256("ES256", EcKeyUtil.EC),
    ES384("ES384", EcKeyUtil.EC),
    ES512("ES512", EcKeyUtil.EC),
    ;

    private final String alg;
    private final String keyType;

    TokenSigningAlg(String alg, String keyType) {
        this.alg = alg;
        this.keyType = keyType;
    }

    public String getAlg() {
        return alg;
    }

    public String getKeyType() {
        return keyType;
    }
}
