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
package com.fujieid.jap.core.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import com.xkcoding.json.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * http servlet request util
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.1
 */
public class RequestUtil {

    /**
     * Get the url parameter value of the request through {@code request.getParameter(paramName)}
     *
     * @param paramName parameter name
     * @param request   current HTTP request
     * @return string
     */
    public static String getParam(String paramName, HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        return request.getParameter(paramName);
    }

    /**
     * Get request header
     *
     * @param headerName request header name
     * @param request    current HTTP request
     * @return string
     */
    public static String getHeader(String headerName, HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        return request.getHeader(headerName);
    }

    /**
     * Get the referer of the current HTTP request
     *
     * @param request current HTTP request
     * @return string
     */
    public static String getReferer(HttpServletRequest request) {
        return getHeader("Referer", request);
    }

    /**
     * Get subdomain name
     *
     * @param request current HTTP request
     * @return string
     */
    public static String getFullDomainName(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * Get the User-Agent of the current HTTP request
     *
     * @param request current HTTP request
     * @return string
     */
    public static String getUa(HttpServletRequest request) {
        return getHeader("User-Agent", request);
    }

    /**
     * Get the IP of the current HTTP request
     *
     * @param request current HTTP request
     * @return string
     */
    public static String getIp(HttpServletRequest request) {
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
        return !StringUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
    }

    /**
     * Get the request url
     *
     * @param encode  Whether to encode url
     * @param request current HTTP request
     * @return string
     */
    public static String getRequestUrl(boolean encode, HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        String currentUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (!StringUtil.isEmpty(queryString)) {
            currentUrl = currentUrl + "?" + queryString;
        }

        if (encode) {
            String result = "";
            try {
                result = URLEncoder.encode(currentUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                //ignore
            }
            return result;
        }

        return currentUrl;
    }

    /**
     * Get the value of the cookie
     *
     * @param request current HTTP request
     * @param name    cookie name
     * @return String
     */
    public static String getCookieVal(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * Get cookie
     *
     * @param request current HTTP request
     * @param name    cookie name
     * @return Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * Get all the cookies, and use the cookie name as the key to form a map
     *
     * @param request current HTTP request
     * @return Map
     */
    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (ArrayUtil.isEmpty(cookies)) {
            return MapUtil.empty();
        }

        return Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, v -> v, (k1, k2) -> k1));
    }

    /**
     * Set cookie
     *
     * @param response current HTTP response
     * @param name     cookie name
     * @param value    cookie value
     * @param maxAge   maxAge
     * @param path     path
     * @param domain   domain
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        if (null != domain) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
    }
}
