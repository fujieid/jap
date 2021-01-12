package com.fujieid.jap.core;

import com.fujieid.jap.core.exception.JapUserException;

/**
 * Abstract the user-related function interface, which is implemented by the caller business system.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 14:21
 * @since 1.0.0
 */
public interface JapUserService {

    /**
     * Get user info by userid.
     *
     * @param userId User id of the business system
     * @return JapUser
     */
    default JapUser getById(String userId) {
        throw new JapUserException("JapUserService#getById(String) must be overridden by subclass");
    }

    /**
     * Get user info by username.
     *
     * @param username username of the business system
     * @return JapUser
     */
    default JapUser getByName(String username) {
        throw new JapUserException("JapUserService#getByName(String) must be overridden by subclass");
    }

    /**
     * Verify that the password entered by the user matches
     *
     * @param password The password in the HTML-based login form
     * @param user     User information that is queried by the user name in the HTML form
     * @return {@code boolean} When true is returned, the password matches, otherwise the password is wrong
     */
    default boolean validPassword(String password, JapUser user) {
        throw new JapUserException("JapUserService#getByName(String) must be overridden by subclass");
    }

}
