package com.fujieid.jap.httpapi.strategy;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.http.JapHttpRequest;

/**
 * Implement this functional interface if your auth info in the request body is not standard json format.
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
@FunctionalInterface
public interface RequestBodyToJapUserStrategy {
    /**
     * get a JapUser from request
     *
     * @param request request
     * @return JapUser
     * @throws Exception When the data in the request cannot be decoded, an exception will be thrown
     */
    JapUser decode(JapHttpRequest request) throws Exception;
}
