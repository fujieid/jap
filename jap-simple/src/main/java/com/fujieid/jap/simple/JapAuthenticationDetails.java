package com.fujieid.jap.simple;

import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * A holder of HTTP details related to common authentication requests.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 11:25
 * @since 1.0.0
 */
public class JapAuthenticationDetails implements Serializable {

    private final String clientIp;

    private final String remoteAddress;

    private final String userAgent;

    private final String sessionId;

    public JapAuthenticationDetails(HttpServletRequest request) {

        this.clientIp = ServletUtil.getClientIP(request);
        this.remoteAddress = request.getRemoteAddr();
        this.userAgent = ServletUtil.getHeader(request, "user-agent", "UTF-8");

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
