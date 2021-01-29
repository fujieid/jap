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
package com.fujieid.jap.oauth2.pkce;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.fujieid.jap.oauth2.Oauth2Util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Proof Key for Code Exchange by OAuth Public Client
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://tools.ietf.org/html/rfc7636" target="_blank">Proof Key for Code Exchange by OAuth Public Clients</a>
 * @since 1.0.0
 */
public class PkceUtil {

    private static final TimedCache<String, String> timedCache = CacheUtil.newTimedCache(TimeUnit.MINUTES.toMillis(5));

    /**
     * Create the parameters required by PKCE
     *
     * @param pkceCodeChallengeMethod After the pkce enhancement protocol is enabled, the generation method of challenge
     *                                code derived from the code verifier sent in the authorization request is `s256` by default
     * @param params                  oauth request params
     * @see <a href="https://tools.ietf.org/html/rfc7636#section-1.1" target="_blank">1.1. Protocol Flow</a>
     * @see <a href="https://tools.ietf.org/html/rfc7636#section-4.1" target="_blank">4.1. Client Creates a Code Verifier</a>
     * @see <a href="https://tools.ietf.org/html/rfc7636#section-4.2" target="_blank">4.2. Client Creates the Code Challenge</a>
     * @see <a href="https://tools.ietf.org/html/rfc7636#section-4.3" target="_blank"> Client Sends the Code Challenge with the Authorization Request</a>
     */
    public static void addPkceParameters(PkceCodeChallengeMethod pkceCodeChallengeMethod, Map<String, Object> params) {
        if (PkceCodeChallengeMethod.S256 == pkceCodeChallengeMethod) {
            String codeVerifier = Oauth2Util.generateCodeVerifier();
            String codeChallenge = Oauth2Util.generateCodeChallenge(pkceCodeChallengeMethod, codeVerifier);
            params.put(PkceParams.CODE_CHALLENGE, codeChallenge);
            params.put(PkceParams.CODE_CHALLENGE_METHOD, pkceCodeChallengeMethod);
            // FIXME 需要考虑分布式环境，例如使用 Redis 缓存
            timedCache.put(PkceParams.CODE_VERIFIER, codeVerifier);

        }
    }

    /**
     * Gets the {@code code_verifier} in the cache
     *
     * @return {@code code_verifier}
     */
    public static String getCacheCodeVerifier() {
        return timedCache.get(PkceParams.CODE_VERIFIER);
    }
}
