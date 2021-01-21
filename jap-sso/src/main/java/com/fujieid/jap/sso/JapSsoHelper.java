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
package com.fujieid.jap.sso;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.fujieid.jap.sso.config.JapSsoConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jap sso helper.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-01-20 16:01
 * @since 1.0.0
 */
public class JapSsoHelper {

    /**
     * Write user information into cookie after successful login
     *
     * @param userId       当前登录用户的id
     * @param username     当前登录用户的name
     * @param japSsoConfig sso config
     * @param request      current request
     * @param response     current response
     */
    public static void login(Object userId, String username, JapSsoConfig japSsoConfig, HttpServletRequest request, HttpServletResponse response) {
        // Initialize Jap SSO config to prevent NPE
        japSsoConfig = null == japSsoConfig ? new JapSsoConfig() : japSsoConfig;
        // Reset kisso config
        SSOConfig ssoConfig = SSOHelper.getSsoConfig();
        ssoConfig.setCookieDomain(japSsoConfig.getCookieDomain());
        ssoConfig.setCookieName(japSsoConfig.getCookieName());
        ssoConfig.setParamReturnUrl(japSsoConfig.getParamReturnUrl());
        ssoConfig.setLoginUrl(japSsoConfig.getLoginUrl());
        ssoConfig.setLogoutUrl(japSsoConfig.getLogoutUrl());
        SSOHelper.setSsoConfig(ssoConfig);
        // set jap cookie
        SSOHelper.setCookie(request, response,
            new SSOToken()
                .setId(userId)
                .setIssuer(username)
                .setIp(request)
                .setUserAgent(request),
            true
        );
    }

    /**
     * Check the login status to determine whether the current user exists in the cookie
     *
     * @param request current request
     * @return The ID of the current login user
     */
    public static String checkLogin(HttpServletRequest request) {
        SSOToken ssoToken = SSOHelper.getSSOToken(request);
        return null == ssoToken ? null : ssoToken.getId();
    }

    /**
     * Log out and clear cookie content
     *
     * @param request  current request
     * @param response current response
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        SSOHelper.clearLogin(request, response);
    }
}
