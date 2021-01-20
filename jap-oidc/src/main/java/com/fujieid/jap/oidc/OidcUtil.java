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
package com.fujieid.jap.oidc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.exception.OidcException;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2020/12/3 14:13
 * @since 1.0.0
 */
public class OidcUtil {

    private static final String DISCOVERY_URL = "/.well-known/openid-configuration";

    /**
     * Get the IDP service configuration
     *
     * @param issuer IDP identity providers, such as `https://sign.fujieid.com`
     * @return OidcDiscoveryDto
     */
    public static OidcDiscoveryDto getOidcDiscovery(String issuer) {
        if (StrUtil.isBlank(issuer)) {
            throw new OidcException("Missing IDP Discovery Url.");
        }
        String discoveryUrl = issuer.concat(DISCOVERY_URL);

        HttpResponse httpResponse = HttpRequest.get(discoveryUrl).execute();
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.body());
        if (CollectionUtil.isEmpty(jsonObject)) {
            throw new OidcException("Unable to parse IDP service discovery configuration information.");
        }
        return new OidcDiscoveryDto()
                .setIssuer(jsonObject.getString(OidcDiscoveryParams.ISSUER))
                .setAuthorizationEndpoint(jsonObject.getString(OidcDiscoveryParams.AUTHORIZATION_ENDPOINT))
                .setTokenEndpoint(jsonObject.getString(OidcDiscoveryParams.TOKEN_ENDPOINT))
                .setUserinfoEndpoint(jsonObject.getString(OidcDiscoveryParams.USERINFO_ENDPOINT))
                .setEndSessionEndpoint(jsonObject.getString(OidcDiscoveryParams.END_SESSION_ENDPOINT))
                .setJwksUri(jsonObject.getString(OidcDiscoveryParams.JWKS_URI));
    }


}
