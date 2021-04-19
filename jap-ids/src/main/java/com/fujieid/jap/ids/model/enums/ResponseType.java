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
 * Response Type
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.1
 */
public enum ResponseType {
    /**
     * https://tools.ietf.org/html/rfc6749#section-3.1.1
     * https://tools.ietf.org/html/rfc6749#section-4.1.1
     */
    CODE("code"),
    /**
     * "token" for requesting an access token (implicit grant) as described
     * https://tools.ietf.org/html/rfc6749#section-4.2.1
     */
    TOKEN("token"),
    /**
     * a registered extension value as described by Section 8.4.
     * https://tools.ietf.org/html/rfc6749#section-8.4
     */
    ID_TOKEN("id_token"),
    ID_TOKEN_TOKEN("id_token token"),
    CODE_ID_TOKEN("code id_token"),
    CODE_TOKEN("code token"),
    CODE_ID_TOKEN_TOKEN("code id_token token"),
    /**
     * https://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#none
     */
    NONE("none");

    private final String type;

    ResponseType(String type) {
        this.type = type;
    }

    public static List<String> responseTypes() {
        return Arrays.stream(ResponseType.values())
            .map(ResponseType::getType)
            .collect(Collectors.toList());
    }

    public String getType() {
        return type;
    }
}
