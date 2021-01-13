package com.fujieid.jap.core;

/**
 * For the user information saved in the JAP system, the developer needs to convert the user information in the
 * business system into JapUser.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 14:48
 * @since 1.0.0
 */
public class JapUser {

    /**
     * The id of the user in the developer's business system
     */
    private String userId;

    /**
     * User name in the developer's business system
     */
    private String username;

    /**
     * User password in developer's business system
     */
    private String password;

    /**
     * Additional information about users in the developer's business system returned when obtaining user data.
     * Please do not save private data, such as secret keys, token.
     */
    private Object additional;

    public String getUserId() {
        return userId;
    }

    public JapUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public JapUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JapUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public Object getAdditional() {
        return additional;
    }

    public JapUser setAdditional(Object additional) {
        this.additional = additional;
        return this;
    }
}
