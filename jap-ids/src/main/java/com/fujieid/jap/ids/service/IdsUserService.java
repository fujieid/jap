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

import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.UserInfo;

/**
 * User-related interface
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IdsUserService {

    /**
     * Login with account and password.
     * <p>
     * In the business system, if it is a multi-tenant business architecture, a user may exist in multiple systems,
     * <p>
     * and the client id can distinguish the system where the user is located
     *
     * @param username account number
     * @param password password
     * @param clientId The unique code of the currently logged-in client
     * @return UserInfo
     */
    default UserInfo loginByUsernameAndPassword(String username, String password, String clientId) {
        throw new IdsException("Not implemented `IdsUserService.loginByUsernameAndPassword(String, String, String)`");
    }

    /**
     * Get user info by userid.
     *
     * @param userId userId of the business system
     * @return UserInfo
     */
    default UserInfo getById(String userId) {
        throw new IdsException("Not implemented `IdsUserService.getById(String)`");
    }

    /**
     * Get user info by username.
     * <p>
     * In the business system, if it is a multi-tenant business architecture, a user may exist in multiple systems,
     * <p>
     * and the client id can distinguish the system where the user is located
     *
     * @param username username of the business system
     * @param clientId The unique code of the currently logged-in client
     * @return UserInfo
     */
    default UserInfo getByName(String username, String clientId) {
        throw new IdsException("Not implemented `IdsUserService.getByName(String, String)`");
    }
}
