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

import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.exception.InvalidTokenException;
import com.fujieid.jap.ids.model.AccessToken;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.ScopeClaimsMapping;
import com.fujieid.jap.ids.util.OauthUtil;
import com.fujieid.jap.ids.util.TokenUtil;
import com.xkcoding.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * userinfo endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#UserInfo" target="_blank">5.3.  UserInfo Endpoint</a>
 * @since 1.0.0
 */
public class UserInfoEndpoint extends AbstractEndpoint {

    /**
     * Get the currently logged-in user information through the access token
     *
     * @param request current request
     * @return IdsResponse
     * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#UserInfo" target="_blank">5.3.  UserInfo Endpoint</a>
     * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#UserInfoResponse" target="_blank">5.3.2.  Successful UserInfo Response</a>
     * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#ScopeClaims" target="_blank">5.4.  Requesting Claims using Scope Values</a>
     */
    public IdsResponse<String, Object> getCurrentUserInfo(HttpServletRequest request) {

        String accessTokenStr = TokenUtil.getAccessToken(request);

        AccessToken accessToken = TokenUtil.getByAccessToken(accessTokenStr);

        if (null == accessToken) {
            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }
        UserInfo user = JapIds.getContext().getUserService().getById(accessToken.getUserId());

        if (null == user) {
            throw new IdsException(ErrorResponse.ACCESS_DENIED);
        }

        String scope = accessToken.getScope();
        Set<String> scopes = OauthUtil.convertStrToList(scope);
        Map<String, Object> userInfoMap = JsonUtil.parseKv(JsonUtil.toJsonString(user));
        // This scope value requests access to the End-User's default profile Claims,
        // which are: name, family_name, given_name, middle_name, nickname, preferred_username, profile, picture, website, gender, birthdate, zoneinfo, locale, and updated_at.
        if (!scopes.contains("profile")) {
            ScopeClaimsMapping scopeClaimsMapping = ScopeClaimsMapping.profile;
            List<String> claims = scopeClaimsMapping.getClaims();
            for (String claim : claims) {
                userInfoMap.remove(claim);
            }
        }
        // This scope value requests access to the email and email_verified Claims.
        if (scopes.contains("email")) {
            ScopeClaimsMapping scopeClaimsMapping = ScopeClaimsMapping.email;
            List<String> claims = scopeClaimsMapping.getClaims();
            for (String claim : claims) {
                userInfoMap.remove(claim);
            }
        }
        // This scope value requests access to the phone_number and phone_number_verified Claims.
        if (scopes.contains("phone")) {
            ScopeClaimsMapping scopeClaimsMapping = ScopeClaimsMapping.phone;
            List<String> claims = scopeClaimsMapping.getClaims();
            for (String claim : claims) {
                userInfoMap.remove(claim);
            }
        }
        // This scope value requests access to the address Claim.
        if (scopes.contains("address")) {
            ScopeClaimsMapping scopeClaimsMapping = ScopeClaimsMapping.address;
            List<String> claims = scopeClaimsMapping.getClaims();
            for (String claim : claims) {
                userInfoMap.remove(claim);
            }
        }
        IdsResponse<String, Object> idsResponse = new IdsResponse<>();
        idsResponse.putAll(userInfoMap);
        return idsResponse;
    }

}
