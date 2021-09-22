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
package com.fujieid.jap.sso;

import com.baomidou.kisso.common.util.StringUtils;
import com.baomidou.kisso.security.token.SSOToken;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.RequestUtil;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapSsoUtil {

    public static SSOToken createSsoToken(Object userId, String username, JapHttpRequest request) {
        return new SSOToken()
            .setId(userId)
            .setIssuer(username)
            .setIp(getIp(request))
            .setUserAgent(RequestUtil.getUa(request))
            .setTime(System.currentTimeMillis());
    }

    public static String createToken(Object userId, String username, JapHttpRequest request) {
        return createSsoToken(userId, username, request).getToken();
    }

    public static SSOToken parseToken(String token) {
        try {
            return SSOToken.parser(token);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getIp(JapHttpRequest request) {
        if (null == request) {
            return "";
        }
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip;
        for (String header : headers) {
            ip = request.getHeader(header);
            if (isValidIp(ip)) {
                return getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }

    /**
     * Obtain the first non-unknown ip address from the multi-level reverse proxy
     *
     * @param ip IP
     * @return The first non-unknown ip address
     */
    private static String getMultistageReverseProxyIp(String ip) {
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (isValidIp(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * Verify ip legitimacy
     *
     * @param ip ip
     * @return boolean
     */
    private static boolean isValidIp(String ip) {
        return !StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
    }
}
