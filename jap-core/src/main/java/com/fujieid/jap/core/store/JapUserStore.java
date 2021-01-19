package com.fujieid.jap.core.store;

import com.fujieid.jap.core.JapUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Save, delete and obtain the login user information.By default, based on local caching,
 * developers can use different caching schemes to implement the interface
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 18:50
 * @since 1.0.0
 */
public interface JapUserStore {

    /**
     * Login completed, save user information to the cache
     *
     * @param request current request
     * @param japUser User information after successful login
     * @return JapUser
     */
    JapUser save(HttpServletRequest request, JapUser japUser);

    /**
     * Clear user information from cache
     *
     * @param request current request
     */
    void remove(HttpServletRequest request);

    /**
     * Get the login user information from the cache, return {@code JapUser} if it exists,
     * return {@code null} if it is not logged in or the login has expired
     *
     * @param request  current request
     * @param response current response
     * @return JapUser
     */
    JapUser get(HttpServletRequest request, HttpServletResponse response);
}
