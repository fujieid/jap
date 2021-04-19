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
package com.fujieid.jap.simple;

import com.fujieid.jap.core.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * A holder of HTTP details related to common authentication requests.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapAuthenticationDetails implements Serializable {

    private final String clientIp;

    private final String remoteAddress;

    private final String userAgent;

    private final String sessionId;

    public JapAuthenticationDetails(HttpServletRequest request) {

        this.clientIp = RequestUtil.getIp(request);
        this.remoteAddress = request.getRemoteAddr();
        this.userAgent = RequestUtil.getHeader("user-agent", request);

        HttpSession session = request.getSession(false);
        this.sessionId = (session != null) ? session.getId() : null;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getSessionId() {
        return sessionId;
    }
}
