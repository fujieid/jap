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
package com.fujieid.jap.ids;

import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.context.IdsContext;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Authorization service based on RFC6749 protocol specification and OpenID Connect Core 1.0 specification
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapIds implements Serializable {
    private static IdsContext context;

    private JapIds() {
    }

    public static void registerContext(IdsContext idsContext) {
        context = idsContext;
    }

    public static IdsContext getContext() {
        if (null == context) {
            throw new IdsException("Unregistered ids context.Please use `JapIds.registerContext(IdsContext)` to register ids context.");
        }
        return context;
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return null != getUserInfo(request);
    }

    public static void saveUserInfo(UserInfo userInfo, HttpServletRequest request) {
        request.getSession().setAttribute(IdsConsts.OAUTH_USERINFO_CACHE_KEY, userInfo);
    }

    public static UserInfo getUserInfo(HttpServletRequest request) {
        return (UserInfo) request.getSession().getAttribute(IdsConsts.OAUTH_USERINFO_CACHE_KEY);
    }

    public static void removeUserInfo(HttpServletRequest request) {
        request.getSession().removeAttribute(IdsConsts.OAUTH_USERINFO_CACHE_KEY);
    }

    public static IdsConfig getIdsConfig() {
        IdsContext context = getContext();
        return context.getIdsConfig();
    }
}
