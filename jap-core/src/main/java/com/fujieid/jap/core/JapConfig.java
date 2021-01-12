package com.fujieid.jap.core;

/**
 * Jap configuration.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 14:19
 * @since 1.0.0
 */
public class JapConfig {

    /**
     * Save login state in session, defaults to {@code true}
     */
    private boolean session;

    /**
     * After successful login, redirect to {@code successRedirect}. Default is `/`
     */
    private String successRedirect = "/";

    /**
     * Prompt message after successful login
     */
    private String successMessage;

    /**
     * After failed login, redirect to {@code failureRedirect}. Default is `/error`
     */
    private String failureRedirect = "/error";

    /**
     * Prompt message after login failed
     */
    private String failureMessage;

    public boolean isSession() {
        return session;
    }

    public JapConfig setSession(boolean session) {
        this.session = session;
        return this;
    }

    public String getSuccessRedirect() {
        return successRedirect;
    }

    public JapConfig setSuccessRedirect(String successRedirect) {
        this.successRedirect = successRedirect;
        return this;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public JapConfig setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
        return this;
    }

    public String getFailureRedirect() {
        return failureRedirect;
    }

    public JapConfig setFailureRedirect(String failureRedirect) {
        this.failureRedirect = failureRedirect;
        return this;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public JapConfig setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        return this;
    }
}
