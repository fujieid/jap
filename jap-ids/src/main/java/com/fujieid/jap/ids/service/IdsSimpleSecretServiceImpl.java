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
package com.fujieid.jap.ids.service;

import com.xkcoding.json.util.StringUtil;

/**
 * Implementation of IdsSecretService that uses the {@code String.equals} function
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsSimpleSecretServiceImpl implements IdsSecretService {

    /**
     * Verify the encoded secret obtained from storage matches the submitted raw
     * secret after it too is encoded. Returns true if the secret match, false if
     * they do not. The stored secret itself is never decoded.
     *
     * @param rawSecret     the raw secret to encode and match
     * @param encodedSecret the encoded secret from storage to compare with
     * @return true if the raw secret, after encoding, matches the encoded secret from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawSecret, String encodedSecret) {
        return !StringUtil.isEmpty(encodedSecret) && rawSecret.equals(encodedSecret);
    }
}
