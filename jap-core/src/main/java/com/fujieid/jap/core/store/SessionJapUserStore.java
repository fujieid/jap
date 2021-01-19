package com.fujieid.jap.core.store;

import com.fujieid.jap.core.JapConst;
import com.fujieid.jap.core.JapUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 19:03
 * @since 1.0.0
 */
public class SessionJapUserStore implements JapUserStore {

    /**
     * Login completed, save user information to the cache
     *
     * @param request current request
     * @param japUser User information after successful login
     * @return JapUser
     */
    @Override
    public JapUser save(HttpServletRequest request, JapUser japUser) {
        HttpSession session = request.getSession();
        japUser.setPassword(null);
        session.setAttribute(JapConst.SESSION_USER_KEY, japUser);
        return japUser;
    }

    /**
     * Clear user information from cache
     *
     * @param request current request
     */
    @Override
    public void remove(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(JapConst.SESSION_USER_KEY);
    }

    /**
     * Get the login user information from the cache, return {@code JapUser} if it exists,
     * return {@code null} if it is not logged in or the login has expired
     *
     * @param request  current request
     * @param response current response
     * @return JapUser
     */
    @Override
    public JapUser get(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        return (JapUser) session.getAttribute(JapConst.SESSION_USER_KEY);
    }
}
