/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.social;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.exception.JapSocialException;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import com.fujieid.jap.core.util.RequestUtil;
import com.xkcoding.json.JsonUtil;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
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
 * @since 1.0.0
 */
public class SocialStrategy extends AbstractJapStrategy {

    private static final String BIND_USERID_PREFIX_CACHE_KEY = "jap:social:bind:userid:";
    private static final String BIND_REFERER_PREFIX_CACHE_KEY = "jap:social:bind:referer:";
    private AuthStateCache authStateCache;

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public SocialStrategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     * @param japCache       japCache
     */
    public SocialStrategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache) {
        super(japUserService, japConfig, japCache);
    }

    /**
     * Use this function to instantiate a policy when using a custom cache.
     * <p>
     * For more information on how to implement custom caching,
     * please refer to: https://justauth.wiki/features/customize-the-state-cache.html
     *
     * @param japUserService Required, implement user operations
     * @param japConfig      Required, jap config
     * @param authStateCache Optional, custom cache implementation class
     */
    public SocialStrategy(JapUserService japUserService, JapConfig japConfig, AuthStateCache authStateCache) {
        this(japUserService, japConfig);
        this.authStateCache = authStateCache;
    }

    /**
     * Use this function to instantiate a policy when using a custom cache.
     * <p>
     * This constructor supports passing in custom {@link JapCache} and {@link AuthStateCache}
     * <p>
     * For more information on how to implement custom state caching,
     * please refer to: https://justauth.wiki/features/customize-the-state-cache.html
     *
     * @param japUserService Required, implement user operations
     * @param japConfig      Required, jap config
     * @param japCache       japCache
     * @param authStateCache Optional, custom cache implementation class
     */
    public SocialStrategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache, AuthStateCache authStateCache) {
        this(japUserService, japConfig, japCache);
        this.authStateCache = authStateCache;
    }

    /**
     * Log in through a third-party platform.
     * <p>
     * 1. The user initiates a login request.
     * <p>
     * 2. Judge whether the user is logged in, if it is logged in, go directly to step [9], otherwise go to step [3].
     * <p>
     * 3. Obtain {@link AuthRequest} according to {@link AuthenticateConfig}.
     * <p>
     * 4. Determine whether the current request is a third-party callback request.
     * If it is a third-party callback request, parse the callback parameters and skip to step [6] to log in.
     * If it is not a third-party callback request, go to step [5].
     * <p>
     * 5. Create an authorization link and return to the front end to jump.
     * <p>
     * 6. Use the third-party callback parameter code to exchange token, token to exchange user info.
     * <p>
     * 7. Determine whether the current third-party user exists in the database,
     * if it exists, go directly to step [9], otherwise go to step [8].
     * <p>
     * 8. Save third-party user information in the database.
     * <p>
     * 9. Log in successfully.
     *
     * @param config   {@link AuthenticateConfig}, the actual type is {@link SocialConfig}
     * @param request  The request to authenticate
     * @param response The response to authenticate
     * @return JapResponse
     */
    @Override
    public JapResponse authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {

        SocialConfig socialConfig = (SocialConfig) config;

        if (socialConfig.isBindUser()) {
            return this.bind(config, request, response);
        }
        JapUser sessionUser = this.checkSession(request, response);
        if (null != sessionUser) {
            return JapResponse.success(sessionUser);
        }

        AuthRequest authRequest = null;
        try {
            authRequest = this.getAuthRequest(config);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }

        String source = socialConfig.getPlatform();

        AuthCallback authCallback = this.parseRequest(request);

        if (this.isCallback(source, authCallback)) {
            try {
                return this.login(request, response, source, authRequest, authCallback, this::loginSuccess);
            } catch (JapUserException e) {
                return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
            }
        }

        // If it is not a callback request, it must be a request to jump to the authorization link
        String url = authRequest.authorize(socialConfig.getState());
        return JapResponse.success(url);
    }

    /**
     * Bind the account of the third-party platform.
     * <p>
     * 1. The user initiates a request to bind an account and caches the current referer address (optional, that is,
     * the address when the binding is initiated, and can jump back to the address after the binding is successful).
     * <p>
     * 2. Obtain {@link AuthRequest} according to {@link AuthenticateConfig}.
     * <p>
     * 3. Determine whether the current request is a third-party callback request.
     * If it is a third-party callback request, parse the callback parameters and skip to step [5] for binding.
     * If it is not a third-party callback request, go to step [4].
     * <p>
     * 4. Create an authorized connection (need to mark the unique identifier of the currently logged in user),
     * and return to the front end to jump.
     * <p>
     * 4.1 You can put the user id in the state.
     * <p>
     * 4.2 The user id and state can be cached correspondingly, the key value is state.
     * <p>
     * 5. Use the third-party callback parameter code to exchange token, token to exchange user info,
     * and parse state to obtain the system user ID to be bound.
     * <p>
     * 6. Determine whether the current third-party user exists in the database,
     * if it exists, go directly to step [8], otherwise go to step [7]
     * <p>
     * 7. Save third-party user information in the database
     * <p>
     * 8. The social userinfo exists in the database, and the binding relationship is established directly
     * with the user id corresponding to the state
     * <p>
     * 9.Bind successfully,
     *
     * @param config   {@link AuthenticateConfig}, the actual type is {@link SocialConfig}
     * @param request  The request to bind
     * @param response The response to bind
     * @return JapResponse
     */
    public JapResponse bind(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {
        SocialConfig socialConfig = (SocialConfig) config;

        if (StrUtil.isEmpty(socialConfig.getBindUserId())) {
            return JapResponse.error(JapErrorCode.ERROR.getErrroCode(), "Unable to bind the account of the third-party platform, the user id is empty.");
        }

        AuthRequest authRequest = null;
        try {
            authRequest = this.getAuthRequest(config);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }

        String source = socialConfig.getPlatform();

        AuthCallback authCallback = this.parseRequest(request);

        if (this.isCallback(source, authCallback)) {
            try {
                return this.login(request, response, source, authRequest, authCallback, (japUser, currentRequest, currentResponse) -> {
                    String bindUserId = authStateCache.get(BIND_USERID_PREFIX_CACHE_KEY.concat(authCallback.getState()));
                    if (StrUtil.isEmpty(bindUserId)) {
                        throw new JapUserException("Unable to bind user information of " + source +
                            ". The operation has expired, please try again");
                    }

                    boolean bindResult = japUserService.bindSocialUser(japUser, bindUserId);
                    if (!bindResult) {
                        throw new JapUserException("Unable to bind user information of " + source +
                            ". bind user id: " + bindUserId +
                            ", social userinfo: " + JsonUtil.toJsonString(japUser));
                    }
                    String referer = authStateCache.get(BIND_REFERER_PREFIX_CACHE_KEY.concat(authCallback.getState()));

                    Map<String, String> res = new HashMap<>(5);
                    res.put("bingUserId", bindUserId);
                    res.put("referer", referer);
                    return JapResponse.success(res);
                });
            } catch (JapUserException e) {
                return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
            }
        }

        // If it is not a callback request, it must be a request to jump to the authorization link
        authStateCache.cache(BIND_USERID_PREFIX_CACHE_KEY.concat(socialConfig.getState()), socialConfig.getBindUserId());
        authStateCache.cache(BIND_REFERER_PREFIX_CACHE_KEY.concat(socialConfig.getState()), RequestUtil.getReferer(request));
        String url = authRequest.authorize(socialConfig.getState());
        return JapResponse.success(url);
    }

    public JapResponse refreshToken(AuthenticateConfig config, AuthToken authToken) {
        AuthRequest authRequest = null;
        try {
            authRequest = this.getAuthRequest(config);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        SocialConfig socialConfig = (SocialConfig) config;
        String source = socialConfig.getPlatform();

        AuthResponse<?> authUserAuthResponse = null;
        try {
            authUserAuthResponse = authRequest.refresh(authToken);
        } catch (Exception e) {
            throw new JapSocialException("Third party refresh access token of `" + source + "` failed. " + e.getMessage());
        }
        if (!authUserAuthResponse.ok() || ObjectUtil.isNull(authUserAuthResponse.getData())) {
            throw new JapUserException("Third party refresh access token of `" + source + "` failed. " + authUserAuthResponse.getMsg());
        }

        authToken = (AuthToken) authUserAuthResponse.getData();
        return JapResponse.success(authToken);
    }

    public JapResponse revokeToken(AuthenticateConfig config, AuthToken authToken) {
        AuthRequest authRequest = null;
        try {
            authRequest = this.getAuthRequest(config);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        SocialConfig socialConfig = (SocialConfig) config;
        String source = socialConfig.getPlatform();

        AuthResponse<?> authUserAuthResponse = null;
        try {
            authUserAuthResponse = authRequest.revoke(authToken);
        } catch (Exception e) {
            throw new JapSocialException("Third party refresh access token of `" + source + "` failed. " + e.getMessage());
        }
        if (!authUserAuthResponse.ok() || ObjectUtil.isNull(authUserAuthResponse.getData())) {
            throw new JapUserException("Third party refresh access token of `" + source + "` failed. " + authUserAuthResponse.getMsg());
        }

        return JapResponse.success();
    }

    public JapResponse getUserInfo(AuthenticateConfig config, AuthToken authToken) {
        AuthRequest authRequest = null;
        try {
            authRequest = this.getAuthRequest(config);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        SocialConfig socialConfig = (SocialConfig) config;
        String source = socialConfig.getPlatform();

        String funName = "getUserInfo";
        Method method = null;
        AuthUser res = null;
        JapUserException japUserException = new JapUserException("Failed to obtain user information on the third-party platform `" + source + "`");
        try {
            if ((method = ReflectUtil.getMethod(authRequest.getClass(), funName, AuthToken.class)) != null) {
                method.setAccessible(true);
                res = ReflectUtil.invoke(authRequest, method, authToken);
                if (null == res) {
                    throw japUserException;
                }
            }
        } catch (Exception e) {
            throw japUserException;
        }
        AuthUser authUser = res;
        return JapResponse.success(authUser);
    }

    private AuthRequest getAuthRequest(AuthenticateConfig config) {
        // Convert AuthenticateConfig to SocialConfig
        this.checkAuthenticateConfig(config, SocialConfig.class);
        SocialConfig socialConfig = (SocialConfig) config;
        String source = socialConfig.getPlatform();

        // Get the AuthConfig of JustAuth
        AuthConfig authConfig = socialConfig.getJustAuthConfig();
        if (ObjectUtil.isNull(authConfig)) {
            throw new JapException(JapErrorCode.MISS_AUTH_CONFIG);
        }

        // Instantiate the AuthRequest of JustAuth
        return JustAuthRequestContext.getRequest(source, socialConfig, authConfig, authStateCache);
    }

    /**
     * Login with third party authorization
     *
     * @param request      Third party callback request
     * @param response     current HTTP response
     * @param source       Third party platform name
     * @param authRequest  AuthRequest of justauth
     * @param authCallback Parse the parameters obtained by the third party callback request
     * @param callback     callback function
     */
    private JapResponse login(HttpServletRequest request, HttpServletResponse response, String source,
                              AuthRequest authRequest, AuthCallback authCallback, SocialFunc callback) throws JapUserException {
        AuthResponse<?> authUserAuthResponse = null;
        try {
            authUserAuthResponse = authRequest.login(authCallback);
        } catch (Exception e) {
            throw new JapSocialException("Third party login of `" + source + "` failed. " + e.getMessage());
        }
        if (!authUserAuthResponse.ok() || ObjectUtil.isNull(authUserAuthResponse.getData())) {
            throw new JapUserException("Third party login of `" + source + "` cannot obtain user information. "
                + authUserAuthResponse.getMsg());
        }

        AuthUser socialUser = (AuthUser) authUserAuthResponse.getData();
        JapUser japUser = japUserService.getByPlatformAndUid(source, socialUser.getUuid());
        if (ObjectUtil.isNull(japUser)) {
            japUser = japUserService.createAndGetSocialUser(socialUser);
            if (ObjectUtil.isNull(japUser)) {
                throw new JapUserException("Unable to save user information of " + source);
            }
        }

        return callback.exec(japUser, request, response);
    }

    /**
     * Whether it is the callback request after the authorization of the third-party platform is completed,
     * the judgment basis is as follows:
     * - Code is not empty
     *
     * @param source       Third party platform name
     * @param authCallback Parameters resolved by callback request
     * @return When true is returned, the current HTTP request is a callback request
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
