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
import com.baomidou.kisso.service.ConfigurableAbstractKissoService;
import com.fujieid.jap.sso.config.JapSsoConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jap sso helper.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapSsoHelper {

    /**
     * Write user information into cookie after successful login
     *
     * @param userId       The ID of the current login user
     * @param username     The name of the current login user
     * @param japSsoConfig sso config
     * @param request      current HTTP request
     * @param response     current HTTP response
     * @return String
     */
    public static String login(Object userId, String username, JapSsoConfig japSsoConfig, HttpServletRequest request, HttpServletResponse response) {
        // Initialize Jap SSO config to prevent NPE
        japSsoConfig = null == japSsoConfig ? new JapSsoConfig() : japSsoConfig;
        // Reset kisso config
        resetKissoConfig(japSsoConfig);
        // set jap cookie
        SSOToken ssoToken = JapSsoUtil.createSsoToken(userId, username, request);
        KiSsoHelper.setCookie(request, response, ssoToken, true);
        return ssoToken.getToken();
    }

    /**
     * init kisso config
     *
     * @param japSsoConfig sso config
     */
    public static void initKissoConfig(JapSsoConfig japSsoConfig) {
        // init kisso config
        SSOConfig ssoConfig = resetKissoConfig(japSsoConfig);
        KiSsoHelper.setKissoService(new JapConfigurableAbstractKissoService(ssoConfig));
    }

    /**
     * reset kisso config
     *
     * @param japSsoConfig sso config
     * @return kisso config
     */
    private static SSOConfig resetKissoConfig(JapSsoConfig japSsoConfig) {
        // Reset kisso config
        SSOConfig ssoConfig = KiSsoHelper.getSsoConfig();
        ssoConfig.setCookieDomain(japSsoConfig.getCookieDomain());
        ssoConfig.setCookieName(japSsoConfig.getCookieName());
        ssoConfig.setParamReturnUrl(japSsoConfig.getParamReturnUrl());
        ssoConfig.setCookieMaxAge(japSsoConfig.getCookieMaxAge());
        KiSsoHelper.setSsoConfig(ssoConfig);
        return ssoConfig;
    }

    /**
     * Check the login status to determine whether the current user exists in the cookie
     *
     * @param request current HTTP request
     * @return The ID of the current login user
     */
    public static String checkLogin(HttpServletRequest request) {
        SSOToken ssoToken = KiSsoHelper.getSSOToken(request);
        return null == ssoToken ? null : ssoToken.getId();
    }

    /**
     * Log out and clear cookie content
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        KiSsoHelper.clearLogin(request, response);
    }

    /**
     * Kisso Single sign-on service abstract implementation class
     *
     * @author YongWu zheng
     * @since 1.0.0
     */
    static class JapConfigurableAbstractKissoService extends ConfigurableAbstractKissoService {

        public JapConfigurableAbstractKissoService(SSOConfig ssoConfig) {
            super();
            config = ssoConfig;
        }
    }

    /**
     * kisso SSO helper
     *
     * @author YongWu zheng
     * @since 1.0.0
     */
    static class KiSsoHelper extends SSOHelper {

        public static void setKissoService(JapConfigurableAbstractKissoService japKissoService) {
            kissoService = japKissoService;
        }
    }


}
