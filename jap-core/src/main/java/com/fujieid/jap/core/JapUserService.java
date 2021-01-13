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
     * Get user information in the current system by social platform and social user id
     *
     * @param platform social platform，refer to {@code me.zhyd.oauth.config.AuthSource#getName()}
     * @param uid      social user id
     * @return JapUser
     */
    default JapUser getByPlatformAndUid(String platform, String uid) {
        throw new JapUserException("JapUserService#getByPlatformAndUid(String, String) must be overridden by subclass");
    }

    /**
     * Verify that the password entered by the user matches
     *
     * @param password The password in the HTML-based login form
     * @param user     User information that is queried by the user name in the HTML form
     * @return {@code boolean} When true is returned, the password matches, otherwise the password is wrong
     */
    default boolean validPassword(String password, JapUser user) {
        throw new JapUserException("JapUserService#validPassword(String, JapUser) must be overridden by subclass");
    }

    /**
     * Save the social login user information to the database and return JapUser
     *
     * @param authUser User information obtained through justauth third-party login, type {@code me.zhyd.oauth.model.AuthUser}
     * @return JapUser Return JapUser, if the save is successful, otherwise return null
     */
    default JapUser createAndGetSocialUser(Object authUser) {
        throw new JapUserException("JapUserService#createSocialUser(String) must be overridden by subclass");
    }

}
