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

import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsUserStoreServiceImpl implements IdsUserStoreService {

    /**
     * Save user data, and store user information in {@link HttpSession} by default.
     * <p>
     * Developers can implement this method to save user information in other media, such as cache, database, etc.
     *
     * @param userInfo User information after login
     * @param request  current HTTP request
     */
    @Override
    public void save(UserInfo userInfo, HttpServletRequest request) {
        request.getSession().setAttribute(IdsConsts.OAUTH_USERINFO_CACHE_KEY, userInfo);
    }

    /**
     * Get logged-in user information
     *
     * @param request current HTTP request
     * @return UserInfo
     */
    @Override
    public UserInfo get(HttpServletRequest request) {
        return (UserInfo) request.getSession().getAttribute(IdsConsts.OAUTH_USERINFO_CACHE_KEY);
    }

    /**
     * Delete logged-in user information
     *
     * @param request current HTTP request
     */
    @Override
    public void remove(HttpServletRequest request) {
        request.getSession().removeAttribute(IdsConsts.OAUTH_USERINFO_CACHE_KEY);
    }
}
