package com.fujieid.jap.social;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
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
        if (!ClassUtil.isAssignable(config.getClass(), SocialConfig.class)) {
            throw new JapSocialException("Social#Unsupported config parameter, please use SocialConfig, a subclass of JapConfig");
        }

        if (this.checkSession(request, response)) {
            return;
        }

        // Convert AuthenticateConfig to SocialConfig
        SocialConfig socialConfig = (SocialConfig) config;
        String source = socialConfig.getPlatform();

        // Get the AuthConfig of JustAuth
        AuthConfig authConfig = (AuthConfig) japConfig.getOptions();

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
     * @param source       Identification of the third party platform specified by the user
     * @param authRequest  AuthRequest of justauth
     * @param authCallback Parse the parameters obtained by the third party callback request
     */
    private void login(HttpServletRequest request, HttpServletResponse response, String source, AuthRequest authRequest, AuthCallback authCallback) {
        AuthResponse<AuthUser> authUserAuthResponse = authRequest.login(authCallback);
        if (!authUserAuthResponse.ok()) {
            throw new JapUserException("Third party login failed to obtain user information. " + authUserAuthResponse.getMsg());
        }

        AuthUser socialUser = authUserAuthResponse.getData();
        JapUser japUser = japUserService.getByPlatformAndUid(source, socialUser.getUuid());
        if (ObjectUtil.isNull(japUser)) {
            japUser = japUserService.createAndGetSocialUser(socialUser);
            if (ObjectUtil.isNull(japUser)) {
                throw new JapUserException("Failed to save third-party user information.");
            }
        }

        if (japConfig.isSession()) {
            HttpSession session = request.getSession();
            japUser.setPassword(null);
            session.setAttribute(JapConst.SESSION_USER_KEY, japUser);
        }
        try {
            response.sendRedirect(japConfig.getSuccessRedirect());
        } catch (IOException e) {
            throw new JapException("JAP failed to redirect via HttpServletResponse.", e);
        }
    }

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
        return !StringUtils.isEmpty(code);
    }

    /**
     * Parse the callback request and get the callback parameter object
     *
     * @param request Current callback request
     * @return AuthCallback
     */
    private AuthCallback parseRequest(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        params.forEach((key, val) -> {
            if (ObjectUtil.isNotNull(val)) {
                jsonObject.put(key, val[0]);
            }
        });
        return jsonObject.toJavaObject(AuthCallback.class);
    }
}
