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
package com.fujieid.jap.simple;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.util.RequestUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The local authentication strategy authenticates requests based on the credentials submitted through an HTML-based
 * login form.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleStrategy extends AbstractJapStrategy {

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public SimpleStrategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     * @param japCache       japCache
     */
    public SimpleStrategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache) {
        super(japUserService, japConfig, japCache);
    }

    @Override
    public JapResponse authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {
        // Convert AuthenticateConfig to SimpleConfig
        try {
            this.checkAuthenticateConfig(config, SimpleConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        SimpleConfig simpleConfig = (SimpleConfig) config;

        JapUser sessionUser = null;
        try {
            sessionUser = this.checkSessionAndCookie(simpleConfig, request, response);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        if (null != sessionUser) {
            return JapResponse.success(sessionUser);
        }

        UsernamePasswordCredential credential = this.doResolveCredential(request, simpleConfig);
        if (null == credential) {
            return JapResponse.error(JapErrorCode.MISS_CREDENTIALS);
        }
        JapUser user = japUserService.getByName(credential.getUsername());
        if (null == user) {
            return JapResponse.error(JapErrorCode.NOT_EXIST_USER);
        }

        boolean valid = japUserService.validPassword(credential.getPassword(), user);
        if (!valid) {
            return JapResponse.error(JapErrorCode.INVALID_PASSWORD);
        }

        return this.loginSuccess(simpleConfig, credential, user, request, response);
    }

    /**
     * login successful
     *
     * @param simpleConfig Authenticate Config
     * @param credential   Username password credential
     * @param user         Jap user
     * @param request      The request to authenticate
     * @param response     The response to authenticate
     */
    private JapResponse loginSuccess(SimpleConfig simpleConfig, UsernamePasswordCredential credential, JapUser user, HttpServletRequest request, HttpServletResponse response) {
        if (credential.isRememberMe()) {
            String cookieDomain = ObjectUtil.isNotEmpty(simpleConfig.getRememberMeCookieDomain()) ? simpleConfig.getRememberMeCookieDomain() : null;
            // add cookie
            RequestUtil.setCookie(response,
                simpleConfig.getRememberMeCookieKey(),
                this.encodeCookieValue(user, simpleConfig),
                simpleConfig.getRememberMeCookieExpire(),
                "/",
                cookieDomain
            );
        }
        return this.loginSuccess(user, request, response);
    }

    /**
     * check session and cookie
     *
     * @param simpleConfig Authenticate Config
     * @param request      The request to authenticate
     * @param response     The response to authenticate
     * @return true to login success, false to login
     */
    private JapUser checkSessionAndCookie(SimpleConfig simpleConfig, HttpServletRequest request, HttpServletResponse response) throws JapException {
        JapUser sessionUser = this.checkSession(request, response);
        if (null != sessionUser) {
            return sessionUser;
        }
        if (!RememberMeUtils.enableRememberMe(request, simpleConfig)) {
            return null;
        }

        Cookie cookie = RequestUtil.getCookie(request, simpleConfig.getRememberMeCookieKey());
        if (ObjectUtil.isNull(cookie)) {
            return null;
        }

        UsernamePasswordCredential credential = this.decodeCookieValue(simpleConfig, cookie.getValue());
        if (ObjectUtil.isNull(credential)) {
            return null;
        }

        JapUser user = japUserService.getByName(credential.getUsername());
        if (null == user) {
            return null;
        }
        // redirect login successful
        this.loginSuccess(user, request, response);
        return user;
    }

    /**
     * decode Username password credential
     *
     * @param simpleConfig Authenticate Config
     * @param cookieValue  Cookie value
     * @return Username password credential
     */
    private UsernamePasswordCredential decodeCookieValue(SimpleConfig simpleConfig, String cookieValue) throws JapException {
        RememberMeDetails details = RememberMeUtils.decode(simpleConfig, cookieValue);
        if (ObjectUtil.isNotNull(details)) {
            // return no longer password and remember me
            return new UsernamePasswordCredential()
                .setUsername(details.getUsername());
        }
        return null;
    }

    /**
     * The value of the encrypted cookie
     *
     * @param user         Jap user
     * @param simpleConfig Authenticate Config
     * @return Encode cookie value string
     */
    private String encodeCookieValue(JapUser user, SimpleConfig simpleConfig) {
        return RememberMeUtils.encode(simpleConfig, user.getUsername()).getEncodeValue();
    }

    /**
     * @param request      The request to authenticate
     * @param simpleConfig Authenticate Config
     * @return Username password credential
     */
    private UsernamePasswordCredential doResolveCredential(HttpServletRequest request, SimpleConfig simpleConfig) {
        String username = request.getParameter(simpleConfig.getUsernameField());
        String password = request.getParameter(simpleConfig.getPasswordField());
        if (null == username || null == password) {
            return null;
        }
        return new UsernamePasswordCredential()
            .setUsername(username)
            .setPassword(password)
            .setRememberMe(
                BooleanUtil.toBoolean(
                    request.getParameter(simpleConfig.getRememberMeField()))
            );
    }
}
