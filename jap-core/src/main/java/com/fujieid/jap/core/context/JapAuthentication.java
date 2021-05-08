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
package com.fujieid.jap.core.context;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.util.JapTokenHelper;
import com.fujieid.jap.core.util.RequestUtil;
import com.xkcoding.json.util.Kv;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Manage the context of jap, after successful login,
 * you can obtain the logged-in user information through jap authentication or execute logout events
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapAuthentication implements Serializable {
    private static JapContext context;

    private JapAuthentication() {
    }

    /**
     * Get JAP Context
     *
     * @return JapContext
     */
    public static JapContext getContext() {
        return context;
    }

    /**
     * Save JAP Context
     *
     * @param japContext JAP Context details
     */
    public static void setContext(JapContext japContext) {
        context = japContext;
    }

    /**
     * Get the currently logged in user
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return JapUser
     */
    public static JapUser getUser(HttpServletRequest request, HttpServletResponse response) {
        if (null == context) {
            return null;
        }
        JapUserStore japUserStore = context.getUserStore();
        if (null == japUserStore) {
            return null;
        }
        return japUserStore.get(request, response);
    }

    /**
     * Check whether the user is logged in. Reference method of use:
     * <p>
     * <code>
     * if(!JapAuthentication.checkUser(request, response).isSuccess()) {
     * // Not logged in.
     * }
     * </code>
     * <p>
     * Is equivalent to the following code：
     *
     * <code>
     * JapUser japUser = JapAuthentication.getUser(request, response);
     * if (null == japUser) {
     * // Not logged in.
     * }
     * </code>
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return JapResponse
     */
    public static JapResponse checkUser(HttpServletRequest request, HttpServletResponse response) {
        JapUser japUser = getUser(request, response);
        if (null == japUser) {
            return JapResponse.error(JapErrorCode.NOT_LOGGED_IN);
        }
        return JapResponse.success(japUser);
    }

    /**
     * Verify the legitimacy of JAP Token
     *
     * @param token jwt token
     * @return Map
     */
    public static Map<String, Object> checkToken(String token) {
        if (null == context || ObjectUtil.isEmpty(token)) {
            return null;
        }
        JapCache japCache = context.getCache();
        if (null == japCache) {
            return null;
        }
        Map<String, Object> tokenMap = JapTokenHelper.checkToken(token);
        if (MapUtil.isNotEmpty(tokenMap)) {
            Kv kv = new Kv();
            kv.putAll(tokenMap);
            // Get the token creation time, multiplied by 1000 is the number of milliseconds
            long iat = kv.getLong("iat") * 1000;
            JapConfig japConfig = context.getConfig();
            // Get token expiration time
            long tokenExpireTime = japConfig.getTokenExpireTime();
            // The token is available when the token creation time plus the token expiration time is later than the current time,
            // otherwise the token has expired
            if (new Date(iat + tokenExpireTime).after(new Date())) {
                return tokenMap;
            }
        }
        return null;
    }

    /**
     * sign out
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return boolean
     */
    public static boolean logout(HttpServletRequest request, HttpServletResponse response) {
        JapUserStore japUserStore = context.getUserStore();
        if (null == japUserStore) {
            return false;
        }
        japUserStore.remove(request, response);

        // Clear all cookie information
        Map<String, Cookie> cookieMap = RequestUtil.getCookieMap(request);
        if (CollectionUtil.isNotEmpty(cookieMap)) {
            cookieMap.forEach((key, cookie) -> {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            });
        }
        return true;
    }

}
