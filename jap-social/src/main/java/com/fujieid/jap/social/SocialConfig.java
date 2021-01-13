package com.fujieid.jap.social;

import com.fujieid.jap.core.AuthenticateConfig;

/**
 * Configuration file of third-party authorization login module
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 13:00
 * @since 1.0.0
 */
public class SocialConfig extends AuthenticateConfig {

    /**
     * The name of a third-party platform regardless of case. For example: gitee、github、google
     */
    private String platform;

    /**
     * An opaque value used by the client to maintain state between the request and callback.  The authorization
     * server includes this value when redirecting the user-agent back to the client
     *
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1.1" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.1.1</a>
     */
    private String state;

    /**
     * Package of {@code AuthRequest} implementation classes
     */
    private String[] scanPackages;

    /**
     * Exclude classes that do not need to be registered, such as: {@code AuthDefaultRequest}、
     * {@code AbstractAuthWeChatEnterpriseRequest}、{@code AuthRequest}
     */
    private String[] exclusionClassNames;

    public String getPlatform() {
        return platform;
    }

    public SocialConfig setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getState() {
        return state;
    }

    public SocialConfig setState(String state) {
        this.state = state;
        return this;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public SocialConfig setScanPackages(String[] scanPackages) {
        this.scanPackages = scanPackages;
        return this;
    }

    public String[] getExclusionClassNames() {
        return exclusionClassNames;
    }

    public SocialConfig setExclusionClassNames(String[] exclusionClassNames) {
        this.exclusionClassNames = exclusionClassNames;
        return this;
    }
}
