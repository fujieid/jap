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
package com.fujieid.jap.ids;

import com.fujieid.jap.core.spi.JapServiceLoader;
import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.context.IdsContext;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.pipeline.IdsFilterPipeline;
import com.fujieid.jap.ids.pipeline.IdsLogoutPipeline;
import com.fujieid.jap.ids.pipeline.IdsSignInPipeline;
import com.fujieid.jap.ids.service.IdsClientDetailService;
import com.fujieid.jap.ids.service.IdsIdentityService;
import com.fujieid.jap.ids.service.IdsUserService;
import com.fujieid.jap.ids.service.IdsUserStoreService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Authorization service based on RFC6749 protocol specification and OpenID Connect Core 1.0 specification
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapIds implements Serializable {
    private static final String UNREGISTERED_IDS_CONTEXT = "Unregistered ids context.Please use `JapIds.registerContext(IdsContext)` to register ids context.";
    private static IdsContext context;

    private JapIds() {
    }

    public static void registerContext(IdsContext idsContext) {
        if (null == context) {
            throw new IdsException(UNREGISTERED_IDS_CONTEXT);
        }
        context = idsContext;

        loadService();

        loadPipeline();
    }

    private static void loadService() {
        if (null == context.getClientDetailService()) {
            context.setClientDetailService(JapServiceLoader.loadFirst(IdsClientDetailService.class));
        }
        if (null == context.getIdentityService()) {
            context.setIdentityService(JapServiceLoader.loadFirst(IdsIdentityService.class));
        }
        if (null == context.getUserService()) {
            context.setUserService(JapServiceLoader.loadFirst(IdsUserService.class));
        }
        if (null == context.getUserStoreService()) {
            context.setUserStoreService(JapServiceLoader.loadFirst(IdsUserStoreService.class));
        }
    }

    private static void loadPipeline() {
        if (null == context.getFilterPipeline()) {
            context.setFilterPipeline(JapServiceLoader.loadFirst(IdsFilterPipeline.class));
        }
        if (null == context.getSigninPipeline()) {
            context.setSigninPipeline(JapServiceLoader.loadFirst(IdsSignInPipeline.class));
        }
        if (null == context.getLogoutPipeline()) {
            context.setLogoutPipeline(JapServiceLoader.loadFirst(IdsLogoutPipeline.class));
        }
    }

    public static IdsContext getContext() {
        if (null == context) {
            throw new IdsException(UNREGISTERED_IDS_CONTEXT);
        }
        return context;
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return null != getUserInfo(request);
    }

    public static void saveUserInfo(UserInfo userInfo, HttpServletRequest request) {
        IdsContext context = getContext();
        context.getUserStoreService().save(userInfo, request);
    }

    public static UserInfo getUserInfo(HttpServletRequest request) {
        IdsContext context = getContext();
        return context.getUserStoreService().get(request);
    }

    public static void removeUserInfo(HttpServletRequest request) {
        IdsContext context = getContext();
        context.getUserStoreService().remove(request);
    }

    public static IdsConfig getIdsConfig() {
        IdsContext context = getContext();
        return context.getIdsConfig();
    }
}
