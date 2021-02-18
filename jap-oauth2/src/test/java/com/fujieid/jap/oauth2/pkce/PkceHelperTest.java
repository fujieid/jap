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

import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.cache.JapCacheContextHolder;
import com.fujieid.jap.core.cache.JapLocalCache;
import com.fujieid.jap.oauth2.OAuthConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * unit test
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class PkceHelperTest {

    @Test
    public void generatePkceParametersNullCodeChallengeMethod() {
        Map<String, Object> pkceInfo = PkceHelper.generatePkceParameters(new OAuthConfig()
            .setClientId("clientId")
            .setCodeVerifierTimeout(1000));
        Assert.assertEquals(2, pkceInfo.size());
        Assert.assertNotNull(pkceInfo.get(PkceParams.CODE_CHALLENGE));
        Assert.assertNotNull(pkceInfo.get(PkceParams.CODE_CHALLENGE_METHOD));
        Assert.assertEquals(PkceCodeChallengeMethod.S256, pkceInfo.get(PkceParams.CODE_CHALLENGE_METHOD));
    }

    @Test
    public void generatePkceParametersPlainCodeChallengeMethod() {
        Map<String, Object> pkceInfo = PkceHelper.generatePkceParameters(new OAuthConfig()
            .setCodeChallengeMethod(PkceCodeChallengeMethod.PLAIN)
            .setClientId("clientId")
            .setCodeVerifierTimeout(1000));
        Assert.assertEquals(2, pkceInfo.size());
        Assert.assertNotNull(pkceInfo.get(PkceParams.CODE_CHALLENGE));
        Assert.assertNotNull(pkceInfo.get(PkceParams.CODE_CHALLENGE_METHOD));
        Assert.assertEquals(PkceCodeChallengeMethod.PLAIN, pkceInfo.get(PkceParams.CODE_CHALLENGE_METHOD));
    }

    @Test
    public void generatePkceParametersS256CodeChallengeMethod() {
        Map<String, Object> pkceInfo = PkceHelper.generatePkceParameters(new OAuthConfig()
            .setCodeChallengeMethod(PkceCodeChallengeMethod.S256)
            .setClientId("clientId")
            .setCodeVerifierTimeout(1000));
        Assert.assertEquals(2, pkceInfo.size());
        Assert.assertNotNull(pkceInfo.get(PkceParams.CODE_CHALLENGE));
        Assert.assertNotNull(pkceInfo.get(PkceParams.CODE_CHALLENGE_METHOD));
        Assert.assertEquals(PkceCodeChallengeMethod.S256, pkceInfo.get(PkceParams.CODE_CHALLENGE_METHOD));
    }

    @Test
    public void getCacheCodeVerifierNullClientId() {
        String res = PkceHelper.getCacheCodeVerifier(null);
        Assert.assertNull(res);
    }

    @Test
    public void getCacheCodeVerifier() {
        JapCache japCache = new JapLocalCache();
        JapCacheContextHolder.enable(japCache);
        JapCacheContextHolder.getCache().set("clientId", "111", 111111);
        String res = PkceHelper.getCacheCodeVerifier("clientId");
        Assert.assertNotNull(res);
        Assert.assertEquals("111", res);
    }
}
