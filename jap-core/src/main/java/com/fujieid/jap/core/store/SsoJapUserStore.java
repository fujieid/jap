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
package com.fujieid.jap.core.store;

import cn.hutool.core.util.StrUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.util.JapTokenHelper;
import com.fujieid.jap.sso.JapSsoHelper;
import com.fujieid.jap.sso.config.JapSsoConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Operation on users in SSO mode (cookie)
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class SsoJapUserStore extends SessionJapUserStore {

    /**
     * Abstract the user-related function interface, which is implemented by the caller business system.
     */
    protected JapUserService japUserService;
    /**
     * Jap Sso configuration.
     */
    protected JapSsoConfig japSsoConfig;

    public SsoJapUserStore(JapUserService japUserService, JapSsoConfig japSsoConfig) {
        this.japUserService = japUserService;
        this.japSsoConfig = japSsoConfig;
    }

    /**
     * Login completed, save user information to the cache
     *
     * @param request  current request
     * @param response current response
     * @param japUser  User information after successful login
     * @return JapUser
     */
    @Override
    public JapUser save(HttpServletRequest request, HttpServletResponse response, JapUser japUser) {
        String token = JapSsoHelper.login(japUser.getUserId(), japUser.getUsername(), this.japSsoConfig, request, response);
        super.save(request, response, japUser);
        new JapTokenHelper(JapAuthentication.getContext().getCache()).saveUserToken(japUser.getUserId(), token);
        return japUser.setToken(token);
    }

    /**
     * Clear user information from cache
     *
     * @param request  current request
     * @param response current response
     */
    @Override
    public void remove(HttpServletRequest request, HttpServletResponse response) {
        JapUser japUser = this.get(request, response);
        if(null != japUser) {
            new JapTokenHelper(JapAuthentication.getContext().getCache()).removeUserToken(japUser.getUserId());
        }
        super.remove(request, response);
        JapSsoHelper.logout(request, response);
    }

    /**
     * Get the login user information from the cache, return {@code JapUser} if it exists,
     * return {@code null} if it is not logged in or the login has expired
     *
     * @param request  current request
     * @param response current response
     * @return JapUser
     */
    @Override
    public JapUser get(HttpServletRequest request, HttpServletResponse response) {
        String userId = JapSsoHelper.checkLogin(request);
        if (StrUtil.isBlank(userId)) {
            // The cookie has expired. Clear session content
            super.remove(request, response);
            return null;
        }
        JapUser sessionUser = super.get(request, response);

        /*
          1. The cookie is not invalid, but the user in the session is invalid.
            retrieve the user information and save it to the session
          2. The user information in the session is inconsistent with the user information in the cookie,
            which indicates that an endpoint logs out and then logs in again.
            At this time, the session needs to be updated
         */
        if (null == sessionUser || !sessionUser.getUserId().equals(userId)) {
            sessionUser = this.japUserService.getById(userId);
            // Back-to-back operation to prevent anomalies
            if (null == sessionUser) {
                return null;
            }
            // Save user information into session
            super.save(request, response, sessionUser);
            return sessionUser;
        }
        return sessionUser;
    }
}
