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

import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.store.SessionJapUserStore;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        this.loginSuccess(user, request, response);
    }

    private UsernamePasswordCredential doResolveCredential(HttpServletRequest request, SimpleConfig simpleConfig) {
        String username = request.getParameter(simpleConfig.getUsernameField());
        String password = request.getParameter(simpleConfig.getPasswordField());
        if (null == username || null == password) {
            throw new JapUserException("Missing credentials");
        }
        return new UsernamePasswordCredential()
                .setUsername(username)
                .setPassword(password);
    }
}
