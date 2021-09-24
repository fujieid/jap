package com.fujieid.jap.httpapi;

import cn.hutool.core.codec.Base64;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.httpapi.enums.AuthSchemaEnum;
import com.fujieid.jap.httpapi.enums.HttpMethodEnum;
import com.fujieid.jap.httpapi.subject.DigestAuthorizationSubject;
import com.fujieid.jap.httpapi.subject.DigestWwwAuthenticateSubject;
import com.fujieid.jap.httpapi.subject.HttpAuthResponse;
import com.fujieid.jap.httpapi.util.*;

/**
 * The strategy for jap-http-api module to request third party system.
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
public class HttpApiStrategy extends AbstractJapStrategy {


    public HttpApiStrategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache) {
        super(japUserService, japConfig, japCache);
    }

    @Override
    public JapResponse authenticate(AuthenticateConfig config, JapHttpRequest request, JapHttpResponse response) {
        try {
            checkAuthenticateConfig(config, HttpApiConfig.class);
        } catch (JapException e) {
            e.printStackTrace();
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        HttpApiConfig httpApiConfig = (HttpApiConfig) config;

        return this.doAuthenticate(httpApiConfig, request, response);
    }

    private JapResponse doAuthenticate(HttpApiConfig config, JapHttpRequest request, JapHttpResponse response) {

        HttpAuthResponse authResponse = null;
        JapUser japUser = null;

        try {
            japUser = getJapUser(request, config);

            if (japUser == null) {
                return JapResponse.error(JapErrorCode.MISS_CREDENTIALS);
            }

            switch (config.getAuthSchema()) {
                case BASIC:
                    authResponse = doBasicAuth(japUser, config, response);
                    break;
                case DIGEST:
                    authResponse = doDigestAuth(japUser, config, response);
                    break;
                case BEARER:
                    authResponse = doBearerAuth(japUser, config, response);
                    break;
                default:
                    break;
            }
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (authResponse != null && authResponse.isSuccess()) {
            if (config.getAuthSchema() == AuthSchemaEnum.BASIC || config.getAuthSchema() == AuthSchemaEnum.DIGEST) {
                japUserService.saveHttpAuthedJapUser(japUser);
            }
            return JapResponse.success(authResponse.getBody());
        } else {
            return JapResponse.error(JapErrorCode.INVALID_PASSWORD);
        }
    }

    private HttpAuthResponse doDigestAuth(JapUser japUser, HttpApiConfig config, JapHttpResponse response) {

        /*
         * send a request to third-party server to get a random number and encryption algorithm
         * see： https://datatracker.ietf.org/doc/html/rfc2069#section-2.1.1
         */
        HttpAuthResponse responseForWWWAuth = HttpAuthUtil.sendRequest(config.getLoginUrl(),
            HttpMethodEnum.GET,
            config.getCustomHeaders(),
            config.getCustomParams(),
            SimpleAuthJsonUtil.getJsonStrByParams(config.getCustomBody()));

        if (responseForWWWAuth == null || responseForWWWAuth.getHeaders() == null) {
            return null;
        }
        HttpAuthResponse result = null;
        String wwwAuthenticate = null;

        try {
            wwwAuthenticate = responseForWWWAuth.getHeaders().get("WWW-Authenticate").get(0).replaceFirst("Digest ", "");
            DigestWwwAuthenticateSubject wwwAuthenticateSubject = SubjectSerializeUtil.deserialize(wwwAuthenticate, DigestWwwAuthenticateSubject.class);

            String username = japUser.getUsername();
            String realm = wwwAuthenticateSubject.getRealm();
            String password = japUser.getPassword();
            String method = config.getHttpMethod().toString().toUpperCase();
            String digestUri = URLUtil.getRelativeUri(config.getLoginUrl());
            String nonce = wwwAuthenticateSubject.getNonce();
            String qop = wwwAuthenticateSubject.getQop();
            String nc = DigestUtil.getNc();
            String cnonce = DigestUtil.getCnonce();
            String opaque = wwwAuthenticateSubject.getOpaque();

            String ha1 = DigestMD5Util.encode(username, realm, password);
            String ha2 = DigestUtil.getHa2ByQop(qop, method, digestUri);
            String qopResponse = DigestUtil.getResponseByQop(ha1, nonce, nc, cnonce, qop, ha2);

            DigestAuthorizationSubject authorizationSubject = new DigestAuthorizationSubject()
                .setCnonce(cnonce)
                .setQop(qop)
                .setNc(nc)
                .setNonce(nonce)
                .setRealm(realm)
                .setUsername(username)
                .setUri(digestUri)
                .setResponse(qopResponse)
                .setAlgorithm(wwwAuthenticateSubject.getAlgorithm())
                .setOpaque(opaque);

            String authStr = SubjectSerializeUtil.serialize(authorizationSubject);

            // send authorization request
            config.getCustomHeaders().put("Authorization", "Digest " + authStr);
            result = HttpAuthUtil.sendRequest(config.getLoginUrl(),
                config.getHttpMethod(),
                config.getCustomHeaders(),
                config.getCustomParams(),
                SimpleAuthJsonUtil.getJsonStrByParams(config.getCustomBody()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpAuthResponse doBasicAuth(JapUser japUser, HttpApiConfig config, JapHttpResponse response) {

        /*
         * see： The 'Basic' HTTP Authentication Scheme：https://datatracker.ietf.org/doc/html/rfc7617
         */
        String basicAuth = "Basic " + Base64.encode(japUser.getUsername() + ":" + japUser.getPassword());
        config.getCustomHeaders().put("authorization", basicAuth);

        return HttpAuthUtil.sendRequest(config.getLoginUrl(),
            config.getHttpMethod(),
            config.getCustomHeaders(),
            config.getCustomParams(),
            SimpleAuthJsonUtil.getJsonStrByParams(config.getCustomBody()));
    }

    private HttpAuthResponse sendBearerTokenAuthRequest(String token, HttpApiConfig config) {
        String bearerToken = "Bearer " + token;
        config.getCustomHeaders().put("Authorization", bearerToken);
        return HttpAuthUtil.sendRequest(config.getLoginUrl(),
            config.getHttpMethod(),
            config.getCustomHeaders(),
            config.getCustomParams(),
            SimpleAuthJsonUtil.getJsonStrByParams(config.getCustomBody()));
    }

    private HttpAuthResponse doBearerAuth(JapUser japUser, HttpApiConfig config, JapHttpResponse response) {

        JapUser japUserInDb = japUserService.getByName(japUser.getUsername());
        String token = null;
        if (japUserInDb == null || japUserInDb.getToken() == null) {
            token = doPreAuthForBearerToken(japUser, config, response);
        } else {
            token = japUserInDb.getToken();
        }

        HttpAuthResponse result = sendBearerTokenAuthRequest(token, config);

        // old token expired, request for new token
        if (result == null || !result.isSuccess()) {
            String newToken = doPreAuthForBearerToken(japUser, config, response);
            result = sendBearerTokenAuthRequest(newToken, config);
        }
        return result;
    }

    /**
     * do a pre-auth in Bearer auth to get token for this user
     *
     * @param japUser  jap user
     * @param config   Http api config
     * @param response current http response
     */
    private String doPreAuthForBearerToken(JapUser japUser, HttpApiConfig config, JapHttpResponse response) {
        HttpAuthResponse httpAuthResponse = null;
        // clean old auth token
        config.getCustomHeaders().remove("Authorization");
        config.getCustomHeaders().remove("authorization");
        switch (config.getForBearerTokenEnum()) {
            case BY_BASIC:
                httpAuthResponse = this.doBasicAuth(japUser, new HttpApiConfig(config, config.getBearerTokenIssueUrl()), response);
                break;
            case BY_DIGEST:
                httpAuthResponse = this.doDigestAuth(japUser, new HttpApiConfig(config, config.getBearerTokenIssueUrl()), response);
                break;
            case BY_HEADER:
                config.getCustomHeaders().put("username", japUser.getUsername());
                config.getCustomHeaders().put("password", japUser.getPassword());
                httpAuthResponse = HttpAuthUtil.sendRequest(config.getLoginUrl(),
                    config.getHttpMethod(),
                    config.getCustomHeaders(),
                    config.getCustomParams(),
                    SimpleAuthJsonUtil.getJsonStrByParams(config.getCustomBody()));
                break;
            case BY_PARAMS:
                config.getCustomParams().put("username", japUser.getUsername());
                config.getCustomParams().put("password", japUser.getPassword());
                httpAuthResponse = HttpAuthUtil.sendRequest(config.getLoginUrl(),
                    config.getHttpMethod(),
                    config.getCustomHeaders(),
                    config.getCustomParams(),
                    SimpleAuthJsonUtil.getJsonStrByParams(config.getCustomBody()));
                break;
            case BY_BODY:
                String body = SimpleAuthJsonUtil.getJsonStrByJapUserAndParams(japUser, config.getCustomBody());
                httpAuthResponse = HttpAuthUtil.sendRequest(config.getLoginUrl(),
                    config.getHttpMethod(),
                    config.getCustomHeaders(),
                    config.getCustomParams(),
                    body);
                break;
            default:
                break;
        }

        // pre-auth field
        if (httpAuthResponse == null || !httpAuthResponse.isSuccess()) {
            return null;
        }

        // get token from httpAuthResponse body
        String token = config.getGetTokenFromResponseStrategy().getToken(httpAuthResponse.getBody());
        if (token == null || token.length() == 0) {
            return null;
        }

        // save jap user to db
        japUser.setToken(token);
        japUserService.saveHttpAuthedJapUser(japUser);
        return token;
    }

    /**
     * get user information from request according to http api config.
     *
     * @param request current http request
     * @param config  http api config
     * @return jap user contains username and password
     */
    public JapUser getJapUser(JapHttpRequest request, HttpApiConfig config) {
        JapUser japUser = null;
        String username = null;
        String password = null;

        switch (config.getAuthInfoField()) {
            case PARAMS:
                username = request.getParameter("username");
                password = request.getParameter("password");
                japUser = new JapUser().setUsername(username).setPassword(password);
                break;

            case HEADER:
                username = request.getHeader("username");
                password = request.getHeader("password");
                japUser = new JapUser().setUsername(username).setPassword(password);
                break;

            case BODY:
                try {
                    // get auth info body from request body.
                    japUser = config.getRequestBodyToJapUserStrategy().decode(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        if (null == japUser || null == japUser.getUsername()) {
            return null;
        }

        return japUser;
    }


    /**
     * check http api config.
     * NOTICE:
     * 1. When use "GET" as request method, custom body is not supported.
     * 2. Can not custom body and params at the same time.
     *
     * @param sourceConfig      The parameters passed in by the caller
     * @param targetConfigClazz The actual parameter class type
     * @throws JapException Jap exception will be thrown when http api config configuration error
     */
    @Override
    protected void checkAuthenticateConfig(AuthenticateConfig sourceConfig, Class<?> targetConfigClazz) throws JapException {
        super.checkAuthenticateConfig(sourceConfig, targetConfigClazz);
        HttpApiConfig config = (HttpApiConfig) sourceConfig;
        if (config == null ||
            config.getAuthSchema() == null ||
            config.getHttpMethod() == null ||
            config.getLoginUrl() == null ||
            config.getAuthInfoField() == null ||
            (config.getHttpMethod() == HttpMethodEnum.GET && (config.getCustomBody() != null && config.getCustomBody().size() != 0)) ||
            ((config.getCustomBody() != null && config.getCustomBody().size() != 0) && (config.getCustomParams() != null && config.getCustomParams().size() != 0))
        ) {
            throw new JapException(JapErrorCode.ERROR_HTTP_API_CONFIG);
        }
    }
}
