package com.fujieid.jap.httpapi.strategy;

import com.fujieid.jap.core.JapUser;

import javax.servlet.http.HttpServletRequest;

/**
 *  Implement this functional interface if your auth info in the request body is not standard json format.
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface RequestBodyToJapUserStrategy {
    /**
     * get a JapUser from request
     * @param request request
     * @return JapUser
     * @throws Exception
     */
    JapUser decode(HttpServletRequest request) throws Exception;
}
