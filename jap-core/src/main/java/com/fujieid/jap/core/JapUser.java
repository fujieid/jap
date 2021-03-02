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

import java.io.Serializable;

/**
 * For the user information saved in the JAP system, the developer needs to convert the user information in the
 * business system into JapUser.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapUser implements Serializable {

    private static final long serialVersionUID = 775471329827032075L;
    /**
     * The id of the user in the developer's business system
     */
    private String userId;

    /**
     * User name in the developer's business system
     */
    private String username;

    /**
     * User password in developer's business system
     */
    private String password;

    /**
     * Additional information about users in the developer's business system returned when obtaining user data.
     * Please do not save private data, such as secret keys, token.
     */
    private Object additional;

    private String token;

    public String getUserId() {
        return userId;
    }

    public JapUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public JapUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JapUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public Object getAdditional() {
        return additional;
    }

    public JapUser setAdditional(Object additional) {
        this.additional = additional;
        return this;
    }

    public String getToken() {
        return token;
    }

    public JapUser setToken(String token) {
        this.token = token;
        return this;
    }
}
