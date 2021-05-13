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

import com.fujieid.jap.ids.config.IdsConfig;

/**
 * The verification type when the user verifies the jwt token (access token, refresh token, id token)，
 * For specific usage, please refer to {@link com.fujieid.jap.ids.util.JwtUtil#validateJwtToken(String, String, String, String)}
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum JwtVerificationType {
    /**
     * Using an HTTPS JWKS endpoint
     */
    HTTPS_JWKS_ENDPOINT,
    /**
     * Using JWKs
     */
    JWKS
}
