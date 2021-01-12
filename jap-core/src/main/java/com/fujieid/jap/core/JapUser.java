package com.fujieid.jap.core;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 14:48
 * @since 1.0.0
 */
public class JapUser {

    private String userId;
    private String username;
    private String password;

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
}
