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
package com.fujieid.jap.ids.provider;

import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.ids.exception.InvalidRequestException;
import com.fujieid.jap.ids.model.ClientCertificate;
import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.util.ClientCertificateUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Parameter parser for oauth request
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsRequestParamProvider {

    public static IdsRequestParam parseRequest(HttpServletRequest request) {
        if (ObjectUtil.isEmpty(request.getParameterMap())) {
            throw new InvalidRequestException(ErrorResponse.INVALID_REQUEST);
        }
        IdsRequestParam param = new IdsRequestParam();

        ClientCertificate clientCertificate = ClientCertificateUtil.getClientCertificate(request);
        param.setClientId(clientCertificate.getId());
        param.setClientSecret(clientCertificate.getSecret());

        param.setCode(request.getParameter(IdsConsts.CODE));
        param.setRedirectUri(request.getParameter(IdsConsts.REDIRECT_URI));
        param.setScope(request.getParameter(IdsConsts.SCOPE));
        param.setState(request.getParameter(IdsConsts.STATE));
        param.setGrantType(request.getParameter(IdsConsts.GRANT_TYPE));
        param.setAccessToken(request.getParameter(IdsConsts.ACCESS_TOKEN));
        param.setRefreshToken(request.getParameter(IdsConsts.REFRESH_TOKEN));
        param.setResponseType(request.getParameter(IdsConsts.RESPONSE_TYPE));
        param.setNonce(request.getParameter(IdsConsts.NONCE));
        param.setUid(request.getParameter(IdsConsts.UID));

        param.setResponseMode(request.getParameter(IdsConsts.RESPONSE_MODE));
        param.setDisplay(request.getParameter(IdsConsts.DISPLAY));
        param.setPrompt(request.getParameter(IdsConsts.PROMPT));
        param.setAuthTime(request.getParameter(IdsConsts.MAX_AGE));
        param.setIdTokenHint(request.getParameter(IdsConsts.ID_TOKEN_HINT));
        param.setAcr(request.getParameter(IdsConsts.ACR));
        param.setAutoapprove(request.getParameter(IdsConsts.AUTOAPPROVE));

        // Get username and password Applies to:<a href="https://tools.ietf.org/html/rfc6749#section-4.3" target="_blank">Resource Owner Password Credentials Grant</a>
        param.setUsername(request.getParameter(IdsConsts.USERNAME));
        param.setPassword(request.getParameter(IdsConsts.PASSWORD));

        /*
         * Applicable to open pkce enhanced protocol in authorization code mode
         * @see <a href="https://tools.ietf.org/html/rfc7636" target="_blank">Proof Key for Code Exchange by OAuth Public Clients</a>
         */
        param.setCodeVerifier(request.getParameter(IdsConsts.CODE_VERIFIER));
        param.setCodeChallengeMethod(request.getParameter(IdsConsts.CODE_CHALLENGE_METHOD));
        param.setCodeChallenge(request.getParameter(IdsConsts.CODE_CHALLENGE));
        return param;
    }
}
