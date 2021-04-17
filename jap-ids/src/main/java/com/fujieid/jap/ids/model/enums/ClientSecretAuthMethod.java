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
package com.fujieid.jap.ids.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The client secret authentication method supports the following four situations:
 * <p>
 * 1. Post parameter: {@link ClientSecretAuthMethod#CLIENT_SECRET_POST}
 * <p>
 * 2. The basic format string in the request header:{@link ClientSecretAuthMethod#CLIENT_SECRET_BASIC}
 * <p>
 * 3. url: {@link ClientSecretAuthMethod#NONE}
 * <p>
 * 4. All of the above support: {@link ClientSecretAuthMethod#ALL}
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ClientSecretAuthMethod {
    /**
     * Post parameter
     */
    CLIENT_SECRET_POST,
    /**
     * The basic format string in the request header
     */
    CLIENT_SECRET_BASIC,
    /**
     * url
     */
    NONE,
    /**
     * All of the above support
     */
    ALL;

    public static List<String> getAllMethods() {
        return Arrays.stream(ClientSecretAuthMethod.values())
            .filter((method) -> method != ALL)
            .map((method) -> method.name().toLowerCase())
            .collect(Collectors.toList());
    }

    public String getMethod() {
        return this.name().toLowerCase();
    }
}
