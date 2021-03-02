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
package com.fujieid.jap.core.result;

/**
 * jap system error code
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum JapErrorCode {
    /**
     * jap system error code
     */
    SUCCESS(200, "success."),
    NOT_LOGGED_IN(401, "Not logged in."),
    ERROR(500, "An error occurred in the system, please refer to the error message."),
    NOT_EXIST_USER(1000, "The user does not exist."),
    INVALID_PASSWORD(1001, "Passwords don't match."),
    INVALID_MEMBERME_COOKIE(1002, "Illegal memberme cookie."),
    UNABLE_SAVE_USERINFO(1003, "Unable to save user information."),
    MISS_AUTH_CONFIG(1004, "AuthConfig in SocialStrategy is required."),
    MISS_AUTHENTICATE_CONFIG(1005, "AuthenticateConfig is required."),
    MISS_ISSUER(1006, "OidcStrategy requires a issuer option."),
    MISS_CREDENTIALS(1007, "Missing credentials"),
    ;

    private final int errroCode;
    private final String errorMessage;

    JapErrorCode(int errroCode, String errorMessage) {
        this.errroCode = errroCode;
        this.errorMessage = errorMessage;
    }

    public int getErrroCode() {
        return errroCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
