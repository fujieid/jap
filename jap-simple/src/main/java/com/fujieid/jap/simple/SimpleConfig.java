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

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 18:02
 * @since 1.0.0
 */
public class SimpleConfig extends AuthenticateConfig {

    /**
     * Get the user name from request through {@code request.getParameter(`usernameField`)}, which defaults to "username"
     */
    private String usernameField = "username";
    /**
     * Get the password from request through {@code request.getParameter(`passwordField`)}, which defaults to "password"
     */
    private String passwordField = "password";

    public String getUsernameField() {
        return usernameField;
    }

    public SimpleConfig setUsernameField(String usernameField) {
        this.usernameField = usernameField;
        return this;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public SimpleConfig setPasswordField(String passwordField) {
        this.passwordField = passwordField;
        return this;
    }
}
