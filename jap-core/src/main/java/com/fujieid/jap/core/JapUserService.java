package com.fujieid.jap.core;

import com.alibaba.fastjson.JSONObject;
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
     * Get user info by username.
     * <p>
     * It is suitable for the {@code jap-simple} module
     *
     * @param username username of the business system
     * @return JapUser
     */
    default JapUser getByName(String username) {
        throw new JapUserException("JapUserService#getByName(String) must be overridden by subclass");
    }

    /**
     * Verify that the password entered by the user matches
     * <p>
     * It is suitable for the {@code jap-simple} module
     *
     * @param password The password in the HTML-based login form
     * @param user     User information that is queried by the user name in the HTML form
     * @return {@code boolean} When true is returned, the password matches, otherwise the password is wrong
     */
    default boolean validPassword(String password, JapUser user) {
        throw new JapUserException("JapUserService#validPassword(String, JapUser) must be overridden by subclass");
    }

    /**
     * Get user information in the current system by social platform and social user id
     * <p>
     * It is suitable for the {@code jap-social} module
     *
     * @param platform social platform，refer to {@code me.zhyd.oauth.config.AuthSource#getName()}
     * @param uid      social user id
     * @return JapUser
     */
    default JapUser getByPlatformAndUid(String platform, String uid) {
        throw new JapUserException("JapUserService#getByPlatformAndUid(String, String) must be overridden by subclass");
    }

    /**
     * Save the social login user information to the database and return JapUser
     * <p>
     * It is suitable for the {@code jap-social} module
     *
     * @param userInfo User information obtained through justauth third-party login, type {@code me.zhyd.oauth.model.AuthUser}
     * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
     */
    default JapUser createAndGetSocialUser(Object userInfo) {
        throw new JapUserException("JapUserService#createSocialUser(AuthUser) must be overridden by subclass");
    }

    /**
     * Save the oauth login user information to the database and return JapUser
     * <p>
     * It is suitable for the {@code jap-oauth2} module
     *
     * @param platform oauth2 platform name
     * @param userInfo The basic user information returned by the OAuth platform
     * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
     */
    default JapUser createAndGetOauth2User(String platform, JSONObject userInfo) {
        throw new JapUserException("JapUserService#createAndGetOauth2User(JSONObject) must be overridden by subclass");
    }

}
