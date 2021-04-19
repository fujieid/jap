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
package com.fujieid.jap.ids.filter;

import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.enums.ErrorResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * User status filter to verify the user's login status
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsUserStatusFilter extends AbstractIdsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        boolean ignored = this.isIgnoredServletPath(request);
        if (ignored) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (JapIds.isAuthenticated(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        throw new IdsException(ErrorResponse.INVALID_USER_STATUS);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        String ignoreUrl = filterConfig.getInitParameter("ignoreUrl");
        this.initIgnoreUrls(ignoreUrl);
    }
}
