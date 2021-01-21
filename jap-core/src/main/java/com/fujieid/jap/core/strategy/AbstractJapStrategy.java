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
package com.fujieid.jap.core.strategy;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.JapSocialException;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.store.JapUserStoreContextHolder;
import com.fujieid.jap.core.store.SessionJapUserStore;
import com.fujieid.jap.core.store.SsoJapUserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * General policy handling methods and parameters, policies of other platforms can inherit
 * {@code AbstractJapStrategy}, and override the constructor
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:01
 * @since 1.0.0
 */
public abstract class AbstractJapStrategy implements JapStrategy {

    /**
     * Abstract the user-related function interface, which is implemented by the caller business system.
     */
    protected JapUserService japUserService;
    /**
     * user store
     */
    protected JapUserStore japUserStore;
    /**
     * Jap configuration.
     */
    protected JapConfig japConfig;

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public AbstractJapStrategy(JapUserService japUserService, JapConfig japConfig) {
        this(japUserService, japConfig.isSso() ? new SsoJapUserStore(japUserService, japConfig.getSsoConfig()) : new SessionJapUserStore(), japConfig);
    }

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japUserStore   Jap User storage mode, default is session storage
     * @param japConfig      japConfig
     */
    public AbstractJapStrategy(JapUserService japUserService, JapUserStore japUserStore, JapConfig japConfig) {
        this.japUserService = japUserService;
        this.japUserStore = japUserStore;
        this.japConfig = japConfig;

        JapUserStoreContextHolder.enable(this.japUserStore);
    }

    /**
     * Verify whether the user logs in. If so, jump to {@code japConfig.getSuccessRedirect()}. Otherwise, return {@code false}
     *
     * @param request  Current Authentication Request
     * @param response Current response
     * @return boolean
     */
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) {
        JapUser sessionUser = japUserStore.get(request, response);
        if (null != sessionUser) {
            try {
                response.sendRedirect(japConfig.getSuccessRedirect());
                return true;
            } catch (IOException e) {
                throw new JapException("JAP failed to redirect via HttpServletResponse.", e);
            }
        }
        return false;
    }

    protected void loginSuccess(JapUser japUser, HttpServletRequest request, HttpServletResponse response) {
        japUserStore.save(request, response, japUser);
        try {
            response.sendRedirect(japConfig.getSuccessRedirect());
        } catch (IOException e) {
            throw new JapException("JAP failed to redirect via HttpServletResponse.", e);
        }
    }

    /**
     * Verify that the AuthenticateConfig is of the specified class type
     *
     * @param sourceConfig      The parameters passed in by the caller
     * @param targetConfigClazz The actual parameter class type
     */
    protected void checkAuthenticateConfig(AuthenticateConfig sourceConfig, Class<?> targetConfigClazz) {
        if (ObjectUtil.isNull(sourceConfig)) {
            throw new JapSocialException("SocialConfig is required");
        }
        if (!ClassUtil.isAssignable(sourceConfig.getClass(), targetConfigClazz)) {
            throw new JapSocialException("Unsupported parameter type, please use " + ClassUtil.getClassName(targetConfigClazz, true) + ", a subclass of AuthenticateConfig");
        }
    }
}
