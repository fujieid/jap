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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.OidcException;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2Strategy;

/**
 * OpenID Connect 1.0 is a simple identity layer on top of the OAuth 2.0 protocol.
 * It enables Clients to verify the identity of the End-User based on the authentication performed by an Authorization Server,
 * as well as to obtain basic profile information about the End-User in an interoperable and REST-like manner.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html" target="_blank">OpenID Connect Core 1.0 incorporating errata set 1</a>
 * @since 1.0.0
 */
public class OidcStrategy extends Oauth2Strategy {

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public OidcStrategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     * @param japCache       japCache
     */
    public OidcStrategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache) {
        super(japUserService, japConfig, japCache);
    }

    /**
     * Authenticate request by delegating to a service provider using OAuth 2.0.
     *
     * @param config   OAuthConfig
     * @param request  The request to authenticate
     * @param response The response to authenticate
     */
    @Override
    public JapResponse authenticate(AuthenticateConfig config, JapHttpRequest request, JapHttpResponse response) {

        try {
            this.checkAuthenticateConfig(config, OidcConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        OidcConfig oidcConfig = (OidcConfig) config;
        if (ObjectUtil.isNull(oidcConfig.getIssuer())) {
            return JapResponse.error(JapErrorCode.MISS_ISSUER);
        }

        String issuer = oidcConfig.getIssuer();

        OidcDiscoveryDto discoveryDto = null;

        JapCache japCache = this.japContext.getCache();

        String discoveryCacheKey = OidcConst.DISCOVERY_CACHE_KEY.concat(issuer);
        if (japCache.containsKey(discoveryCacheKey)) {
            discoveryDto = (OidcDiscoveryDto) japCache.get(discoveryCacheKey);
        } else {
            try {
                discoveryDto = OidcUtil.getOidcDiscovery(issuer);
                japCache.set(discoveryCacheKey, discoveryDto);
            } catch (OidcException e) {
                return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
            }
        }

        oidcConfig.setAuthorizationUrl(discoveryDto.getAuthorizationEndpoint())
            .setTokenUrl(discoveryDto.getTokenEndpoint())
            .setUserinfoUrl(discoveryDto.getUserinfoEndpoint());

        OAuthConfig oAuthConfig = BeanUtil.copyProperties(oidcConfig, OAuthConfig.class);
        return super.authenticate(oAuthConfig, request, response);
    }
}
