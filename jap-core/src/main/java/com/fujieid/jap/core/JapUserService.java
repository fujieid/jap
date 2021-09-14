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
package com.fujieid.jap.core;

import java.util.Map;

/**
 * Abstract the user-related function interface, which is implemented by the caller business system.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JapUserService {

    /**
     * Get user info by userid.
     *
     * @param userId userId of the business system
     * @return JapUser
     */
    default JapUser getById(String userId) {
        return null;
    }

    /**
     * Get user info by username.
     * <p>
     * It is suitable for the {@code jap-simple} module
     *
     * @param username username of the business system
     * @return JapUser
     */
    default JapUser getByName(String username) {
        return null;
    }

    /**
     * Verify that the password entered by the user matches
     * <p>
     * It is suitable for the {@code jap-simple} module
     *
     * @param password The password in the HTML-based login form
     * @param user     User information that is queried by the user name in the HTML form
     * @return {@code boolean} When true is returned, the password matches, otherwise the password is wrong
     */
    default boolean validPassword(String password, JapUser user) {
        return false;
    }

    /**
     * Get user information in the current system by social platform and social user id
     * <p>
     * It is suitable for the {@code jap-social} module
     *
     * @param platform social platform，refer to {@code me.zhyd.oauth.config.AuthSource#getName()}
     * @param uid      social user id
     * @return JapUser
     */
    default JapUser getByPlatformAndUid(String platform, String uid) {
        return null;
    }

    /**
     * Save the social login user information to the database and return JapUser
     * <p>
     * It is suitable for the {@code jap-social} module
     *
     * @param userInfo User information obtained through justauth third-party login, type {@code me.zhyd.oauth.model.AuthUser}
     * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
     */
    default JapUser createAndGetSocialUser(Object userInfo) {
        return null;
    }

    /**
     * Save the oauth login user information to the database and return JapUser
     * <p>
     * It is suitable for the {@code jap-oauth2} module
     *
     * @param platform  oauth2 platform name
     * @param userInfo  The basic user information returned by the OAuth platform
     * @param tokenInfo The token information returned by the OAuth platform, developers can store tokens
     *                  , type {@code com.fujieid.jap.oauth2.helper.AccessToken}
     * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
     */
    default JapUser createAndGetOauth2User(String platform, Map<String, Object> userInfo, Object tokenInfo) {
        return null;
    }

    /**
     * Save the http authed user information to the database and return JapUser
     * <p>
     * It is suitable for the {@code jap-http-api} module
     * @param userinfo user information
     * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
     */
    default JapUser createAndGetHttpApiUser(Object userinfo){
        return null;
    }

}
