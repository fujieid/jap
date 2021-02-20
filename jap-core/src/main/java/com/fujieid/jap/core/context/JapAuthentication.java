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
import cn.hutool.extra.servlet.ServletUtil;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUtil;
import com.fujieid.jap.core.store.JapUserStore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
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

    public static JapContext getContext() {
        return context;
    }

    public static void setContext(JapContext japContext) {
        context = japContext;
    }

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

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        JapUserStore japUserStore = context.getUserStore();
        if (null == japUserStore) {
            return;
        }
        japUserStore.remove(request, response);

        // Clear all cookie information
        Map<String, Cookie> cookieMap = ServletUtil.readCookieMap(request);
        if (CollectionUtil.isEmpty(cookieMap)) {
            return;
        }
        cookieMap.forEach((key, cookie) -> {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });

        JapConfig config = context.getConfig();
        if (null != config) {
            JapUtil.redirect(config.getLogoutRedirect(), response);
        }
    }

}
