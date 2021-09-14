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
package com.fujieid.jap.ids.util;

import com.fujieid.jap.core.util.RequestUtil;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.exception.IdsException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Get the request url of each api of the oauth endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.2
 */
public class EndpointUtil {

    public static String getIssuer(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        if (config.isEnableDynamicIssuer() && null == request) {
            throw new IdsException("The second-level domain name verification has been enabled, the HTTP request cannot be empty");
        }
        return config.isEnableDynamicIssuer() ?
            RequestUtil.getFullDomainName(request)
                .concat(Optional
                    .ofNullable(config.getContextPath())
                    .orElse("")) :
            config.getIssuer();
    }


    public static String getLoginUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getLoginUrl();
    }

    public static String getErrorUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getErrorUrl();
    }

    public static String getAuthorizeUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getAuthorizeUrl();
    }

    public static String getAuthorizeAutoApproveUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getAuthorizeAutoApproveUrl();
    }

    public static String getTokenUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getTokenUrl();
    }

    public static String getUserinfoUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getUserinfoUrl();
    }

    public static String getRegistrationUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getRegistrationUrl();
    }

    public static String getEndSessionUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getEndSessionUrl();
    }

    public static String getCheckSessionUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getCheckSessionUrl();
    }

    public static String getLogoutRedirectUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getLogoutRedirectUrl();
    }

    public static String getJwksUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getJwksUrl();
    }

    public static String getDiscoveryUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        return getIssuer(request) + config.getDiscoveryUrl();
    }

    public static String getLoginPageUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        if (config.isExternalLoginPageUrl()) {
            return config.getLoginPageUrl();
        }
        return getIssuer(request) + config.getLoginPageUrl();
    }

    public static String getConfirmPageUrl(HttpServletRequest request) {
        IdsConfig config = JapIds.getIdsConfig();
        if (config.isExternalConfirmPageUrl()) {
            return config.getConfirmPageUrl();
        }
        return getIssuer(request) + config.getConfirmPageUrl();
    }
}
