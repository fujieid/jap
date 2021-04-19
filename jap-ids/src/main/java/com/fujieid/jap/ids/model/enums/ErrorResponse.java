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

import com.xkcoding.json.util.StringUtil;

/**
 * Authorization error code
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1.2.1" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.1.2.1</a>
 * @see <a href="https://tools.ietf.org/html/rfc6749#section-5.2" target="_blank">https://tools.ietf.org/html/rfc6749#section-5.2</a>
 * @since 1.0.1
 */
public enum ErrorResponse {

    /**
     * Authorization error code
     */
    INVALID_REQUEST("invalid_request", "Request parameters are missing or not supported or incorrect."),
    INVALID_CLIENT("invalid_client", "The requested client id or client secret parameter is invalid."),
    INVALID_GRANT("invalid_grant", "Invalid grant, request parameters are incorrect or expired."),
    INVALID_CODE("invalid_code", "The authorization code is invalid or expired."),
    INVALID_USER_CERTIFICATE("invalid_user_certificate", "Invalid user credentials."),
    INVALID_USER_STATUS("invalid_user_status", "Invalid user status, the user may have logged out."),
    INVALID_JWKS("invalid_jwks", "Invalid jwks json. Please check if `IdsConfig.JwtConfig.jwksJson` is configured correctly."),
    INVALID_CODE_CHALLENGE("invalid_code_challenge", "Illegal request, code challenge verification failed."),
    INVALID_TOKEN("invalid_token", "Invalid token (access token, refresh token, or id token)."),
    INVALID_SCOPE("invalid_scope", "The requested scope parameter is invalid, unknown, or the requested permission scope exceeds the permission scope granted by the data owner."),
    INVALID_REDIRECT_URI("invalid_redirect_uri", "The requested callback URL is incorrect."),
    UNSUPPORTED_GRANT_TYPE("unsupported_grant_type", "The grant type is not supported by the authorization server, or the current client is not authorized for the grant type."),
    UNSUPPORTED_RESPONSE_TYPE("unsupported_response_type", "The response type is not supported by the authorization server, or the current client does not allow the response type."),
    ACCESS_DENIED("access_denied", "The authorization server rejected the current request。"),
    SERVER_ERROR("server_error", "The authorization server is temporarily unavailable. Please try again later."),
    AUTHORIZATION_FAILED("authorization_failed", "Authorization failed, please contact the systems administrator."),
    EXPIRED_TOKEN("expired_token", "The requested token has expired (access token, refresh token, or id token)."),
    DISABLED_CLIENT("disabled_client", "The client is not accessible and may have been disabled by the administrator."),
    ;

    private final String error;
    private final String errorDescription;

    ErrorResponse(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public static ErrorResponse getByError(String error) {
        if (StringUtil.isEmpty(error)) {
            return AUTHORIZATION_FAILED;
        }
        ErrorResponse[] errorResponses = ErrorResponse.values();
        for (ErrorResponse errorResponse : errorResponses) {
            if (errorResponse.getError().equalsIgnoreCase(error)) {
                return errorResponse;
            }
        }
        return AUTHORIZATION_FAILED;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
