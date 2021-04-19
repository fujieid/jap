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
package com.fujieid.jap.ids.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.InvalidCodeException;
import com.fujieid.jap.ids.exception.UnsupportedGrantTypeException;
import com.fujieid.jap.ids.model.AuthCode;
import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.util.OauthUtil;

/**
 * oauth 2.0 related methods
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Oauth2ServiceImpl implements Oauth2Service {
    private static final Log log = LogFactory.get();

    @Override
    public String createAuthorizationCode(IdsRequestParam param, UserInfo user, Long codeExpiresIn) {
        String scopeStr = param.getScope();
        String nonce = param.getNonce();
        String code = RandomUtil.randomString(12);

        AuthCode authCode = new AuthCode()
            .setScope(scopeStr)
            .setUser(user)
            .setNonce(nonce)
            .setCodeChallenge(param.getCodeChallenge())
            .setCodeChallengeMethod(param.getCodeChallengeMethod());
        JapIds.getContext().getCache().set(IdsConsts.OAUTH_CODE_CACHE_KEY + code, authCode, codeExpiresIn * 1000);
        return code;
    }

    @Override
    public AuthCode validateAndGetAuthrizationCode(String grantType, String code) {
        if (!GrantType.AUTHORIZATION_CODE.getType().equals(grantType)) {
            throw new UnsupportedGrantTypeException(ErrorResponse.UNSUPPORTED_GRANT_TYPE);
        }
        AuthCode authCode = this.getCodeInfo(code);
        if (null == authCode || ObjectUtil.hasNull(authCode.getUser(), authCode.getScope())) {
            throw new InvalidCodeException(ErrorResponse.INVALID_CODE);
        }
        return authCode;
    }

    @Override
    public void validateAuthrizationCodeChallenge(String codeVerifier, String code) {
        log.debug("客户端开启了 PKCE 增强协议，开始校验 code challenge 的合法性...");
        AuthCode authCode = this.getCodeInfo(code);
        if (ObjectUtil.isNull(authCode)) {
            throw new InvalidCodeException(ErrorResponse.INVALID_CODE);
        }
        if (ObjectUtil.hasNull(authCode.getCodeChallenge(), authCode.getCodeChallengeMethod())) {
            log.debug("客户端开启了 PKCE 增强协议，code challenge 的合法性，校验失败...");
            throw new InvalidCodeException(ErrorResponse.INVALID_CODE_CHALLENGE);
        }
        String codeChallengeMethod = authCode.getCodeChallengeMethod();
        String cacheCodeChallenge = authCode.getCodeChallenge();
        String currentCodeChallenge = OauthUtil.generateCodeChallenge(codeChallengeMethod, codeVerifier);
        if (!currentCodeChallenge.equals(cacheCodeChallenge)) {
            throw new InvalidCodeException(ErrorResponse.INVALID_CODE_CHALLENGE);
        }
    }

    @Override
    public AuthCode getCodeInfo(String code) {
        return (AuthCode) JapIds.getContext().getCache().get(IdsConsts.OAUTH_CODE_CACHE_KEY + code);
    }

    @Override
    public void invalidateCode(String code) {
        JapIds.getContext().getCache().removeKey(IdsConsts.OAUTH_CODE_CACHE_KEY + code);
    }

}
