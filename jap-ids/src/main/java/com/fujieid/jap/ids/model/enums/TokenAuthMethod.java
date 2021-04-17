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

/**
 * When accessing the api, the token usage mode supports the following four situations:
 * <p>
 * 1. Set {@code bearer access token} in the request header: {@link TokenAuthMethod#TOKEN_HEADER}
 * <p>
 * 2. Set access token in cookie: {@link TokenAuthMethod#TOKEN_COOKIE}
 * <p>
 * 3. url: {@link TokenAuthMethod#TOKEN_URL}
 * <p>
 * 4. All of the above support:{@link TokenAuthMethod#ALL}
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum TokenAuthMethod {
    /**
     * request header
     */
    TOKEN_HEADER,
    /**
     * cookie
     */
    TOKEN_COOKIE,
    /**
     * url
     */
    TOKEN_URL,
    /**
     * 支持全部
     */
    ALL
}
