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
package com.fujieid.jap.ids.context;

import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.cache.JapLocalCache;
import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.pipeline.IdsPipeline;
import com.fujieid.jap.ids.service.*;

import java.io.Serializable;

/**
 * ids context
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsContext implements Serializable {

    private JapCache cache = new JapLocalCache();

    private IdsClientDetailService clientDetailService;

    private IdsUserService userService;

    private IdsIdentityService identityService;

    private IdsUserStoreService userStoreService;

    private IdsTokenService tokenService;

    private IdsConfig idsConfig;

    private IdsPipeline<Object> filterPipeline;

    private IdsPipeline<UserInfo> signinPipeline;

    private IdsPipeline<UserInfo> logoutPipeline;

    public JapCache getCache() {
        return cache == null ? new JapLocalCache() : cache;
    }

    public IdsContext setCache(JapCache cache) {
        this.cache = cache;
        return this;
    }

    public IdsClientDetailService getClientDetailService() {
        return clientDetailService;
    }

    public IdsContext setClientDetailService(IdsClientDetailService clientDetailService) {
        this.clientDetailService = clientDetailService;
        return this;
    }

    public IdsUserService getUserService() {
        return userService;
    }

    public IdsContext setUserService(IdsUserService userService) {
        this.userService = userService;
        return this;
    }

    public IdsConfig getIdsConfig() {
        return idsConfig;
    }

    public IdsContext setIdsConfig(IdsConfig idsConfig) {
        this.idsConfig = idsConfig;
        return this;
    }

    public IdsIdentityService getIdentityService() {
        return identityService;
    }

    public IdsContext setIdentityService(IdsIdentityService identityService) {
        this.identityService = identityService;
        return this;
    }

    public IdsUserStoreService getUserStoreService() {
        return userStoreService;
    }

    public IdsContext setUserStoreService(IdsUserStoreService userStoreService) {
        this.userStoreService = userStoreService;
        return this;
    }

    public IdsTokenService getTokenService() {
        return tokenService;
    }

    public IdsContext setTokenService(IdsTokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }

    public IdsPipeline<Object> getFilterPipeline() {
        return filterPipeline;
    }

    public IdsContext setFilterPipeline(IdsPipeline<Object> filterPipeline) {
        this.filterPipeline = filterPipeline;
        return this;
    }

    public IdsPipeline<UserInfo> getSigninPipeline() {
        return signinPipeline;
    }

    public IdsContext setSigninPipeline(IdsPipeline<UserInfo> signinPipeline) {
        this.signinPipeline = signinPipeline;
        return this;
    }

    public IdsPipeline<UserInfo> getLogoutPipeline() {
        return logoutPipeline;
    }

    public IdsContext setLogoutPipeline(IdsPipeline<UserInfo> logoutPipeline) {
        this.logoutPipeline = logoutPipeline;
        return this;
    }
}
