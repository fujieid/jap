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
package com.fujieid.jap.ids.endpoint;

import com.fujieid.jap.ids.exception.UnsupportedGrantTypeException;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.provider.IdsRequestParamProvider;
import com.fujieid.jap.ids.provider.IdsTokenProvider;
import com.fujieid.jap.ids.util.TokenUtil;
import com.xkcoding.json.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Token Endpoint. According to the request parameters, to obtain different types of access tokens, refer to:
 * <p>
 * https://tools.ietf.org/html/rfc6749#section-5
 * <p>
 * https://tools.ietf.org/html/rfc6749#section-6
 * <p>
 * The OAuth 2.0 Authorization Framework: Bearer Token Usage: https://tools.ietf.org/html/rfc6750
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class TokenEndpoint extends AbstractEndpoint {

    private final IdsTokenProvider idsTokenProvider = new IdsTokenProvider(oauth2Service);

    public IdsResponse<String, Object> getToken(HttpServletRequest request) {
        IdsRequestParam param = IdsRequestParamProvider.parseRequest(request);

        if (StringUtil.isEmpty(param.getGrantType())) {
            throw new UnsupportedGrantTypeException(ErrorResponse.UNSUPPORTED_GRANT_TYPE);
        }
        if (GrantType.AUTHORIZATION_CODE.getType().equals(param.getGrantType())) {
            return idsTokenProvider.generateAuthorizationCodeResponse(param, request);
        }
        if (GrantType.PASSWORD.getType().equals(param.getGrantType())) {
            return idsTokenProvider.generatePasswordResponse(param, request);
        }
        if (GrantType.CLIENT_CREDENTIALS.getType().equals(param.getGrantType())) {
            return idsTokenProvider.generateClientCredentialsResponse(param, request);
        }
        if (GrantType.REFRESH_TOKEN.getType().equals(param.getGrantType())) {
            return idsTokenProvider.generateRefreshTokenResponse(param, request);
        }
        throw new UnsupportedGrantTypeException(ErrorResponse.UNSUPPORTED_GRANT_TYPE);
    }

    public IdsResponse<String, Object> revokeToken(HttpServletRequest request) {
        TokenUtil.invalidateToken(request);
        return new IdsResponse<>();
    }

}
