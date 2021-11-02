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

import cn.hutool.core.util.StrUtil;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.exception.InvalidTokenException;
import com.fujieid.jap.ids.model.AccessToken;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.pipeline.IdsPipeline;
import com.fujieid.jap.ids.util.EndpointUtil;
import com.fujieid.jap.ids.util.TokenUtil;

/**
 * Logout Endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class LogoutEndpoint extends AbstractEndpoint {

    public IdsResponse<String, String> logout(JapHttpRequest request, JapHttpResponse response) {
        IdsPipeline<UserInfo> logoutPipeline = JapIds.getContext().getLogoutPipeline();
        logoutPipeline = this.getUserInfoIdsPipeline(logoutPipeline);
        if (!logoutPipeline.preHandle(request, response)) {
            throw new IdsException("IdsLogoutPipeline<UserInfo>.preHandle returns false, the process is blocked.");
        }
        JapIds.removeUserInfo(request);
        request.getSession().invalidate();

        logoutPipeline.afterHandle(request, response);

        String accessTokenStr = TokenUtil.getAccessToken(request);
        AccessToken accessToken = TokenUtil.getByAccessToken(accessTokenStr);
        if (null == accessToken) {
            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }
        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(accessToken.getClientId());
        if (null == clientDetail) {
            throw new InvalidTokenException(ErrorResponse.INVALID_TOKEN);
        }
        String redirectUrl = null;
        if (!StrUtil.isEmpty(clientDetail.getLogoutRedirectUri())) {
            redirectUrl = clientDetail.getLogoutRedirectUri();
        } else {
            redirectUrl = EndpointUtil.getLogoutRedirectUrl(request);
        }
        return new IdsResponse<String, String>().data(redirectUrl);
    }
}
