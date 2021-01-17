package com.fujieid.jap.simple;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/17 15:21
 * @since 1.0.0
 */
public class UsernamePasswordCredential {

    private String username;
    private String password;
    private boolean rememberMe;


    public String getUsername() {
        return username;
    }

    public UsernamePasswordCredential setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordCredential setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public UsernamePasswordCredential setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }
}
