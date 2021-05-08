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

import com.fujieid.jap.core.JapUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Save, delete and obtain the login user information.By default, based on local caching,
 * developers can use different caching schemes to implement the interface
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JapUserStore {

    /**
     * Login completed, save user information to the cache
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param japUser  User information after successful login
     * @return JapUser
     */
    JapUser save(HttpServletRequest request, HttpServletResponse response, JapUser japUser);

    /**
     * Clear user information from cache
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    void remove(HttpServletRequest request, HttpServletResponse response);

    /**
     * Get the login user information from the cache, return {@code JapUser} if it exists,
     * return {@code null} if it is not logged in or the login has expired
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return JapUser
     */
    JapUser get(HttpServletRequest request, HttpServletResponse response);
}
