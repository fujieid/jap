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

import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The local authentication strategy authenticates requests based on the credentials submitted through an HTML-based
 * login form.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 17:53
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
     */
    public SimpleStrategy(JapUserService japUserService, JapUserStore japUserStore, JapConfig japConfig) {
        super(japUserService, japUserStore, japConfig);
    }

    @Override
    public void authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {

        if (this.checkSession(request, response)) {
            return;
        }

        // Convert AuthenticateConfig to SimpleConfig
        this.checkAuthenticateConfig(config, SimpleConfig.class);
        SimpleConfig simpleConfig = (SimpleConfig) config;

        UsernamePasswordCredential credential = this.doResolveCredential(request, simpleConfig);
        JapUser user = japUserService.getByName(credential.getUsername());
        if (null == user) {
            throw new JapUserException("The user does not exist.");
        }

        boolean valid = japUserService.validPassword(credential.getPassword(), user);
        if (!valid) {
            throw new JapUserException("Passwords don't match.");
        }

        if (simpleConfig.isEnableRememberMe() && credential.isRememberMe()) {
            // add cookie
            this.addRememberMeCookie(user, simpleConfig, request, response);
        }

        this.loginSuccess(user, request, response);
    }

    /**
     * math cookie
     *
     * @param simpleConfig config
     * @param request      The request to authenticate
     * @return
     */
    protected String matchRememberMeCookie(SimpleConfig simpleConfig, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (ObjectUtil.equal(simpleConfig.getRememberMeCookieKey(), cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * decode UsernamePasswordCredential
     *
     * @param simpleConfig config
     * @param cookieValue
     * @return
     */
    protected UsernamePasswordCredential decodeCookieValue(SimpleConfig simpleConfig, String cookieValue) {
        SimpleConfig.RememberMeDetails decode = simpleConfig.decode(cookieValue);
        if (ObjectUtil.isNotNull(decode)) {
            // return no longer password and remember me
            return new UsernamePasswordCredential()
                .setUsername(decode.getUsername());
        }
        return null;
    }

    /**
     * authenticate cookie
     *
     * @param config   config
     * @param request  The request to authenticate
     * @param response The response to authenticate
     */
    public void authenticateCookie(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {
        if (this.checkSession(request, response)) {
            return;
        }

        // Convert AuthenticateConfig to SimpleConfig
        this.checkAuthenticateConfig(config, SimpleConfig.class);
        SimpleConfig simpleConfig = (SimpleConfig) config;
        String cookieValue = this.matchRememberMeCookie(simpleConfig, request);

        if (ObjectUtil.isNotNull(cookieValue)) {
            UsernamePasswordCredential credential = this.decodeCookieValue(simpleConfig, cookieValue);
            if (ObjectUtil.isNotNull(cookieValue)) {
                JapUser user = japUserService.getByName(credential.getUsername());
                if (null == user) {
                    throw new JapUserException("The user does not exist.");
                }
                // redirect login successful
                this.loginSuccess(user, request, response);
                return;
            }
        }

        // redirect login url
        try {
            response.sendRedirect(japConfig.getLoginUrl());
        } catch (IOException e) {
            throw new JapException("JAP failed to redirect via HttpServletResponse.", e);
        }
    }

    /**
     * add RememberMe Cookie
     *
     * @param user
     * @param simpleConfig
     * @param request
     * @param response
     */
    protected void addRememberMeCookie(JapUser user, SimpleConfig simpleConfig, HttpServletRequest request, HttpServletResponse response) {
        String cookieValue = this.encodeCookieValue(user, simpleConfig);
        Cookie cookie = new Cookie(simpleConfig.getRememberMeCookieKey(), cookieValue);
        cookie.setMaxAge(simpleConfig.getRememberMeCookieExpire());
        cookie.setPath(this.getRequestPath(request));
        cookie.setSecure(request.isSecure());
        // Custom domain name
        if (ObjectUtil.isNotEmpty(simpleConfig.getRememberMeCookieDomain())) {
            cookie.setDomain(simpleConfig.getRememberMeCookieDomain());
        }
        response.addCookie(cookie);
    }


    /**
     * Clear the cookie to log out of use
     *
     * @param simpleConfig
     * @param request
     * @param response
     */
    public void cancelRememberMeCookie(SimpleConfig simpleConfig, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(simpleConfig.getRememberMeCookieKey(), null);
        cookie.setMaxAge(0);
        cookie.setPath(this.getRequestPath(request));
        // Custom domain name
        if (ObjectUtil.isNotEmpty(simpleConfig.getRememberMeCookieDomain())) {
            cookie.setDomain(simpleConfig.getRememberMeCookieDomain());
        }
        response.addCookie(cookie);
    }

    /**
     * The value of the encrypted cookie
     *
     * @param user
     * @param simpleConfig
     * @return
     */
    protected String encodeCookieValue(JapUser user, SimpleConfig simpleConfig) {
        return simpleConfig.encode(user.getUsername(), user.getPassword()).getEncodeValue();
    }

    /**
     * Get the request path
     *
     * @param request
     * @return
     */
    private String getRequestPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    private UsernamePasswordCredential doResolveCredential(HttpServletRequest request, SimpleConfig simpleConfig) {
        String username = request.getParameter(simpleConfig.getUsernameField());
        String password = request.getParameter(simpleConfig.getPasswordField());
        if (null == username || null == password) {
            throw new JapUserException("Missing credentials");
        }
        boolean rememberMe = this.rememberRequest(request, simpleConfig);
        return new UsernamePasswordCredential()
            .setUsername(username)
            .setPassword(password)
            .setRememberMe(rememberMe);
    }

    /**
     * Retrieve Remember me from the request
     *
     * @param request
     * @param simpleConfig
     * @return
     */
    protected boolean rememberRequest(HttpServletRequest request, SimpleConfig simpleConfig) {
        String rememberParameter = request.getParameter(simpleConfig.getRememberMeField());
        if (rememberParameter != null) {
            return rememberParameter.equalsIgnoreCase("true") || rememberParameter.equalsIgnoreCase("on")
                || rememberParameter.equalsIgnoreCase("yes") || rememberParameter.equals("1");
        }
        return false;
    }
}
