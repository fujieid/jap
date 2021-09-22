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
package com.fujieid.jap.web.filter;

import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.pipeline.IdsPipeline;
import com.xkcoding.json.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ids Filter
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbstractIdsFilter {
    protected final List<String> ignoreUrls = new ArrayList<>();

    /**
     * Whether it is a servlet request that needs to be ignored
     *
     * @param request The current HTTP request to be intercepted
     * @return boolean, the request does not need to be intercepted when true is returned
     */
    protected boolean isIgnoredServletPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (ignoreUrls.contains(servletPath)) {
            return true;
        }
        for (String ignoreUrl : ignoreUrls) {
            if (ignoreUrl.contains("**")) {
                String[] urls = ignoreUrl.split("\\*\\*");
                if (urls.length == 1) {
                    if (servletPath.startsWith(urls[0])) {
                        return true;
                    }
                }
                if (urls.length > 1) {
                    if (servletPath.startsWith(urls[0]) && servletPath.endsWith(urls[urls.length - 1])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Initialize the url of the filter to be released
     *
     * @param ignoreUrl URLs that do not need to be intercepted
     */
    protected void initIgnoreUrls(String ignoreUrl) {
        if (null != ignoreUrl) {
            String[] ignoreUrls = ignoreUrl.split(",");
            this.ignoreUrls.addAll(Arrays.asList(ignoreUrls));
        } else {
            // Fault-tolerant processing
            IdsConfig config = JapIds.getIdsConfig();
            String authorizeUrl = config.getAuthorizeUrl();
            String authorizeAutoApproveUrl = config.getAuthorizeAutoApproveUrl();
            String loginUrl = config.getLoginUrl();
            String loginPageUrl = config.getLoginPageUrl();
            String errorUrl = config.getErrorUrl();
            String confirmPageUrl = config.getConfirmPageUrl();
            String tokenUrl = config.getTokenUrl();
            String registrationUrl = config.getRegistrationUrl();
            String checkSessionUrl = config.getCheckSessionUrl();
            String jwksUrl = config.getJwksUrl();
            String discoveryUrl = config.getDiscoveryUrl();
            String logoutUrl = config.getLoginUrl();
            String logoutRedirectUrl = config.getLogoutRedirectUrl();
            String[] urls = {authorizeUrl, authorizeAutoApproveUrl, loginUrl, loginPageUrl, errorUrl, confirmPageUrl,
                tokenUrl, registrationUrl, jwksUrl, discoveryUrl, logoutUrl, logoutRedirectUrl, checkSessionUrl};
            for (String url : urls) {
                if (StringUtil.isEmpty(url)) {
                    continue;
                }

                this.ignoreUrls.add(url);
            }
        }
        this.ignoreUrls.add("/favicon.ico");
    }

    protected IdsPipeline<Object> getFilterErrorPipeline(IdsPipeline<Object> idsFilterErrorPipeline) {
        if (null == idsFilterErrorPipeline) {
            idsFilterErrorPipeline = new IdsPipeline<Object>() {
                @Override
                public void errorHandle(JapHttpRequest request, JapHttpResponse response, Throwable throwable) {
                    IdsPipeline.super.errorHandle(request, response, throwable);
                }
            };
        }
        return idsFilterErrorPipeline;
    }
}
