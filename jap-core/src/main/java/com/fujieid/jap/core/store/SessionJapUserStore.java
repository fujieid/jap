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

import cn.hutool.core.bean.BeanUtil;
import com.fujieid.jap.core.JapConst;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.util.JapTokenHelper;
import com.fujieid.jap.core.util.JapUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class SessionJapUserStore implements JapUserStore {

    public SessionJapUserStore() {
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
        HttpSession session = request.getSession();
        JapUser newUser = BeanUtil.copyProperties(japUser, JapUser.class);
        newUser.setPassword(null);
        session.setAttribute(JapConst.SESSION_USER_KEY, newUser);

        JapConfig japConfig = JapAuthentication.getContext().getConfig();
        if (!japConfig.isSso()) {
            String token = JapUtil.createToken(japUser, request);
            new JapTokenHelper(JapAuthentication.getContext().getCache()).saveUserToken(japUser.getUserId(), token);
            japUser.setToken(token);
        }
        return japUser;
    }

    /**
     * Clear user information from cache
     *
     * @param request  current request
     * @param response current response
     */
    @Override
    public void remove(HttpServletRequest request, HttpServletResponse response) {

        JapConfig japConfig = JapAuthentication.getContext().getConfig();
        if (!japConfig.isSso()) {
            JapUser japUser = this.get(request, response);
            if (null != japUser) {
                new JapTokenHelper(JapAuthentication.getContext().getCache()).removeUserToken(japUser.getUserId());
            }
        }

        HttpSession session = request.getSession();
        session.removeAttribute(JapConst.SESSION_USER_KEY);
        session.invalidate();
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
        HttpSession session = request.getSession();
        return (JapUser) session.getAttribute(JapConst.SESSION_USER_KEY);
    }
}
