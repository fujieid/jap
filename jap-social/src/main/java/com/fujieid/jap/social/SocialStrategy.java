package com.fujieid.jap.social;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.*;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.JapSocialException;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * automatically complete the authentication logic of third-party login through the policy class when logging in on the
 * social platform.
 * <p>
 * 1. Obtain and jump to the authorization link of the third-party system.
 * 2. Obtain the user information of the third party system through the callback parameters of the third party system.
 * 3. Save the user information of the third-party system to the developer's business system.
 * 4. Login is successful. Jump to the login success page.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 12:08
 * @since 1.0.0
 */
public class SocialStrategy extends AbstractJapStrategy {

    private AuthStateCache authStateCache;

    /**
     * Initialization strategy
     *
     * @param japUserService Required, implement user operations
     */
    public SocialStrategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    /**
     * Use this function to instantiate a policy when using a custom cache.
     * For more information on how to implement custom caching,
     * please refer to: https://justauth.wiki/features/customize-the-state-cache.html
     *
     * @param japUserService Required, implement user operations
     * @param authStateCache Optional, custom cache implementation class
     */
    public SocialStrategy(JapUserService japUserService, JapConfig japConfig, AuthStateCache authStateCache) {
        this(japUserService, japConfig);
        this.authStateCache = authStateCache;
    }

    @Override
    public void authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {

        if (this.checkSession(request, response)) {
            return;
        }

        // Get the AuthConfig of JustAuth
        if (ObjectUtil.isNull(japConfig.getOptions()) || !(japConfig.getOptions() instanceof AuthConfig)) {
            throw new JapSocialException("Options in JapConfig is required and must be of type AuthConfig");
        }
        AuthConfig authConfig = (AuthConfig) japConfig.getOptions();

        // Convert AuthenticateConfig to SocialConfig
        this.checkAuthenticateConfig(config, SocialConfig.class);
        SocialConfig socialConfig = (SocialConfig) config;
        String source = socialConfig.getPlatform();

        // Instantiate the AuthRequest of JustAuth
        AuthRequest authRequest = JustAuthRequestContext.getRequest(source, socialConfig, authConfig, authStateCache);

        AuthCallback authCallback = this.parseRequest(request);

        // If it is not a callback request, it must be a request to jump to the authorization link
        if (!this.isCallback(source, authCallback)) {
            try {
                response.sendRedirect(authRequest.authorize(socialConfig.getState()));
                return;
            } catch (IOException ex) {
                throw new JapException("JAP failed to redirect to " + source + " authorized endpoint through HttpServletResponse.", ex);
            }
        }

        this.login(request, response, source, authRequest, authCallback);
    }

    /**
     * Login with third party authorization
     *
     * @param request      Third party callback request
     * @param response     current response
     * @param source       Third party platform name
     * @param authRequest  AuthRequest of justauth
     * @param authCallback Parse the parameters obtained by the third party callback request
     */
    private void login(HttpServletRequest request, HttpServletResponse response, String source, AuthRequest authRequest, AuthCallback authCallback) {
        AuthResponse<AuthUser> authUserAuthResponse = authRequest.login(authCallback);
        if (!authUserAuthResponse.ok() || ObjectUtil.isNull(authUserAuthResponse.getData())) {
            throw new JapUserException("Third party login of `" + source + "` cannot obtain user information. "
                    + authUserAuthResponse.getMsg());
        }

        AuthUser socialUser = authUserAuthResponse.getData();
        JapUser japUser = japUserService.getByPlatformAndUid(source, socialUser.getUuid());
        if (ObjectUtil.isNull(japUser)) {
            japUser = japUserService.createAndGetSocialUser(socialUser);
            if (ObjectUtil.isNull(japUser)) {
                throw new JapUserException("Unable to save user information of " + source);
            }
        }

        this.loginSuccess(japUser, request, response);
    }

    /**
     * Whether it is the callback request after the authorization of the third-party platform is completed,
     * the judgment basis is as follows:
     * - Code is not empty
     *
     * @param source       Third party platform name
     * @param authCallback Parameters resolved by callback request
     * @return When true is returned, the current request is a callback request
     */
    private boolean isCallback(String source, AuthCallback authCallback) {
        if (source.equals(AuthDefaultSource.TWITTER.name()) && ObjectUtil.isNotNull(authCallback.getOauth_token())) {
            return true;
        }
        String code = authCallback.getCode();
        if (source.equals(AuthDefaultSource.ALIPAY.name())) {
            code = authCallback.getAuth_code();
        } else if (source.equals(AuthDefaultSource.HUAWEI.name())) {
            code = authCallback.getAuthorization_code();
        }
        return !StrUtil.isEmpty(code);
    }

    /**
     * Parse the callback request and get the callback parameter object
     *
     * @param request Current callback request
     * @return AuthCallback
     */
    private AuthCallback parseRequest(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        if (CollectionUtil.isEmpty(params)) {
            return new AuthCallback();
        }
        JSONObject jsonObject = new JSONObject();
        params.forEach((key, val) -> {
            if (ObjectUtil.isNotNull(val)) {
                jsonObject.put(key, val[0]);
            }
        });
        return jsonObject.toJavaObject(AuthCallback.class);
    }
}
