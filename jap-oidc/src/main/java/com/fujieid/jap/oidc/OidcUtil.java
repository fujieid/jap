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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fujieid.jap.core.exception.OidcException;
import com.xkcoding.http.HttpUtil;
import com.xkcoding.json.JsonUtil;

import java.util.Map;

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

        String response = HttpUtil.get(discoveryUrl);
        Map<String, Object> oidcDiscoveryInfo = JsonUtil.toBean(response, Map.class);
        if (CollectionUtil.isEmpty(oidcDiscoveryInfo)) {
            throw new OidcException("Unable to parse IDP service discovery configuration information.");
        }
        return new OidcDiscoveryDto()
            .setIssuer(ObjectUtil.toString(oidcDiscoveryInfo.get(OidcDiscoveryParams.ISSUER)))
            .setAuthorizationEndpoint(ObjectUtil.toString(oidcDiscoveryInfo.get(OidcDiscoveryParams.AUTHORIZATION_ENDPOINT)))
            .setTokenEndpoint(ObjectUtil.toString(oidcDiscoveryInfo.get(OidcDiscoveryParams.TOKEN_ENDPOINT)))
            .setUserinfoEndpoint(ObjectUtil.toString(oidcDiscoveryInfo.get(OidcDiscoveryParams.USERINFO_ENDPOINT)))
            .setEndSessionEndpoint(ObjectUtil.toString(oidcDiscoveryInfo.get(OidcDiscoveryParams.END_SESSION_ENDPOINT)))
            .setJwksUri(ObjectUtil.toString(oidcDiscoveryInfo.get(OidcDiscoveryParams.JWKS_URI)));
    }


}
