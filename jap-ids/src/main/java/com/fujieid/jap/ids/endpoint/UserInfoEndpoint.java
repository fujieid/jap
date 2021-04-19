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
import com.fujieid.jap.ids.util.TokenUtil;
import com.xkcoding.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * userinfo endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserInfoEndpoint extends AbstractEndpoint {

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

        user.setEmail(null);
        user.setPhone_number(null);
        IdsResponse<String, Object> idsResponse = new IdsResponse<>();
        idsResponse.putAll(JsonUtil.parseKv(JsonUtil.toJsonString(user)));
        return idsResponse;
    }

}
