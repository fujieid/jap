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

import cn.hutool.core.util.ArrayUtil;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.InvalidScopeException;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.ResponseType;
import com.fujieid.jap.ids.provider.IdsAuthorizationProvider;
import com.fujieid.jap.ids.provider.IdsRequestParamProvider;
import com.fujieid.jap.ids.util.OauthUtil;
import com.xkcoding.json.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Authorization endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthorizationEndpoint extends AbstractEndpoint {

    private final IdsAuthorizationProvider idsAuthorizationProvider = new IdsAuthorizationProvider(oauth2Service);

    /**
     * Authorize current request
     * <p>
     * When logged in, the method returns the callback url (with parameters such as code)
     * <p>
     * When not logged in, the method returns the login url (with the parameters of the current request)
     *
     * @param request Current request
     * @return Callback url or authorization url
     * @throws IOException IOException
     */
    public IdsResponse<String, Object> authorize(HttpServletRequest request) throws IOException {
        IdsRequestParam param = IdsRequestParamProvider.parseRequest(request);

        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());

        OauthUtil.validClientDetail(clientDetail);
        OauthUtil.validateResponseType(param.getResponseType(), clientDetail.getResponseTypes());
        OauthUtil.validateRedirectUri(param.getRedirectUri(), clientDetail);
        OauthUtil.validateScope(param.getScope(), clientDetail.getScopes());

        if (JapIds.isAuthenticated(request)) {
            UserInfo userInfo = JapIds.getUserInfo(request);
            String url = generateResponseUrl(param, param.getResponseType(), clientDetail, userInfo);
            return new IdsResponse<String, Object>().data(url);
        }

        return new IdsResponse<String, Object>()
            .data(OauthUtil.createAuthorizeUrl(JapIds.getIdsConfig().getLoginPageUrl(), param));
    }

    /**
     * User-initiated consent authorization
     *
     * @param request current request
     * @return Return the callback url (with parameters such as code)
     */
    public IdsResponse<String, Object> agree(HttpServletRequest request) {
        IdsRequestParam param = IdsRequestParamProvider.parseRequest(request);

        // The scope checked by the user may be inconsistent with the scope passed in the current request
        String[] requestScopes = request.getParameterValues("scopes");
        Set<String> scopes = null;
        if (ArrayUtil.isEmpty(requestScopes)) {
            if (StringUtil.isEmpty(param.getScope())) {
                throw new InvalidScopeException(ErrorResponse.INVALID_SCOPE);
            }
            scopes = OauthUtil.convertStrToList(param.getScope()).stream().distinct().collect(Collectors.toSet());
        } else {
            scopes = new TreeSet<>(Arrays.asList(requestScopes));
        }
        // Ultimately participating in the authorized scope
        param.setScope(String.join(" ", scopes));

        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());
        OauthUtil.validClientDetail(clientDetail);

        String responseType = param.getResponseType();
        UserInfo userInfo = JapIds.getUserInfo(request);
        String url = generateResponseUrl(param, responseType, clientDetail, userInfo);
        return new IdsResponse<String, Object>().data(url);
    }

    /**
     * Generate callback url
     *
     * @param param        Parameters in the current request
     * @param responseType oauth authorized response type
     * @param clientDetail Currently authorized client
     * @return Callback url
     */
    private String generateResponseUrl(IdsRequestParam param, String responseType, ClientDetail clientDetail, UserInfo userInfo) {
        if (ResponseType.CODE.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateAuthorizationCodeResponse(userInfo, param, clientDetail);
        }
        if (ResponseType.TOKEN.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateImplicitGrantResponse(userInfo, param, clientDetail);
        }
        if (ResponseType.ID_TOKEN.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateIdTokenAuthorizationResponse(userInfo, param, clientDetail);
        }
        if (ResponseType.ID_TOKEN_TOKEN.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateIdTokenTokenAuthorizationResponse(userInfo, param, clientDetail);
        }
        if (ResponseType.CODE_ID_TOKEN.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateCodeIdTokenAuthorizationResponse(userInfo, param, clientDetail);
        }
        if (ResponseType.CODE_TOKEN.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateCodeTokenAuthorizationResponse(userInfo, param, clientDetail);
        }
        if (ResponseType.CODE_ID_TOKEN_TOKEN.getType().equalsIgnoreCase(responseType)) {
            return idsAuthorizationProvider.generateCodeIdTokenTokenAuthorizationResponse(userInfo, param, clientDetail);
        }
        // none
        return idsAuthorizationProvider.generateNoneAuthorizationResponse(param);
    }

}
