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
package com.fujieid.jap.core.util;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.kisso.security.token.SSOToken;
import com.fujieid.jap.core.JapConst;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.sso.JapSsoUtil;

import java.util.Map;

/**
 * jap user token helper, responsible for processing the token after the user logs in successfully
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapTokenHelper {

    private final JapCache japCache;

    public JapTokenHelper(JapCache japCache) {
        this.japCache = japCache;
    }

    public void saveUserToken(String userId, String token) {
        japCache.set(JapConst.USER_TOKEN_KEY.concat(userId), token);
    }

    public String getUserToken(String userId) {
        return (String) japCache.get(JapConst.USER_TOKEN_KEY.concat(userId));
    }

    public void removeUserToken(String userId) {
        japCache.removeKey(JapConst.USER_TOKEN_KEY.concat(userId));
    }

    public Map<String, Object> checkToken(String token) {
        SSOToken ssoToken = JapSsoUtil.parseToken(token);
        if (ObjectUtil.isNull(ssoToken)) {
            return null;
        }
        String cacheKey = JapConst.USER_TOKEN_KEY.concat(ssoToken.getId());
        if (!japCache.containsKey(cacheKey)) {
            return null;
        }
        return ssoToken.getClaims();
    }
}
