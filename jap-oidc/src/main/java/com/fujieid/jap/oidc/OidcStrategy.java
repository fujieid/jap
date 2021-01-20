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
import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.store.SessionJapUserStore;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2Strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OpenID Connect 1.0 is a simple identity layer on top of the OAuth 2.0 protocol.
 * It enables Clients to verify the identity of the End-User based on the authentication performed by an Authorization Server,
 * as well as to obtain basic profile information about the End-User in an interoperable and REST-like manner.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 16:27
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
        super(japUserService, new SessionJapUserStore(), japConfig);
    }

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public OidcStrategy(JapUserService japUserService, JapUserStore japUserStore, JapConfig japConfig) {
        super(japUserService, japUserStore, japConfig);
    }

    /**
     * Authenticate request by delegating to a service provider using OAuth 2.0.
     *
     * @param config   OAuthConfig
     * @param request  The request to authenticate
     * @param response The response to authenticate
     */
    @Override
    public void authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {

        this.checkAuthenticateConfig(config, OidcConfig.class);
        OidcConfig oidcConfig = (OidcConfig) config;
        this.checkOidcConfig(oidcConfig);

        String issuer = oidcConfig.getIssuer();

        OidcDiscoveryDto discoveryDto = OidcUtil.getOidcDiscovery(issuer);

        oidcConfig.setAuthorizationUrl(discoveryDto.getAuthorizationEndpoint())
                .setTokenUrl(discoveryDto.getTokenEndpoint())
                .setUserinfoUrl(discoveryDto.getUserinfoEndpoint());

        OAuthConfig oAuthConfig = BeanUtil.copyProperties(oidcConfig, OAuthConfig.class);
        super.authenticate(oAuthConfig, request, response);
    }

    private void checkOidcConfig(OidcConfig oidcConfig) {
        if (ObjectUtil.isNull(oidcConfig.getIssuer())) {
            throw new JapOauth2Exception("OidcStrategy requires a issuer option");
        }
    }
}
