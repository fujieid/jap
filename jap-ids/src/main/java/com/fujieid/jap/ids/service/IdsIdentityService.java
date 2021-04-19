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

import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.config.JwtConfig;

/**
 * User/organization/enterprise and other identity service related interfaces
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IdsIdentityService {

    /**
     * Get the jwt token encryption key string, The default is the scoped global jwt config configured in ids config.
     *
     * @param identity User/organization/enterprise identification
     * @return Encryption key string in json format
     */
    default String getJwksJson(String identity) {
        return JapIds.getIdsConfig().getJwtConfig().getJwksJson();
    }

    /**
     * Get the configuration of jwt token encryption, The default is the scoped global jwt config configured in ids config.
     * <p>
     * 调用者需要根据
     *
     * @param clientId The client id of the client that currently needs to be authorized
     * @return Encryption key string in json format
     */
    default JwtConfig getJwtConfig(String clientId) {
        return JapIds.getIdsConfig().getJwtConfig();
    }

}
