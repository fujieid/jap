package com.fujieid.jap.httpapi.strategy;

/**
 * Define how to get token from the third-party server's response when request to issue a bearer token
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
@FunctionalInterface
public interface GetTokenFromResponseStrategy {

    /**
     * get token from body
     *
     * @param body response body
     * @return token
     */
    String getToken(String body);
}
