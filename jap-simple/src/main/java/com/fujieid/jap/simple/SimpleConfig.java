package com.fujieid.jap.simple;

import com.fujieid.jap.core.AuthenticateConfig;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 18:02
 * @since 1.0.0
 */
public class SimpleConfig extends AuthenticateConfig {

    /**
     * Get the user name from request through {@code request.getParameter(`usernameField`)}, which defaults to "username"
     */
    private String usernameField = "username";
    /**
     * Get the password from request through {@code request.getParameter(`passwordField`)}, which defaults to "password"
     */
    private String passwordField = "password";

    public String getUsernameField() {
        return usernameField;
    }

    public SimpleConfig setUsernameField(String usernameField) {
        this.usernameField = usernameField;
        return this;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public SimpleConfig setPasswordField(String passwordField) {
        this.passwordField = passwordField;
        return this;
    }
}
