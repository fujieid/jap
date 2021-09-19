package com.fujieid.jap.httpapi;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.httpapi.enums.AuthInfoFieldEnum;
import com.fujieid.jap.httpapi.enums.AuthSchemaEnum;
import com.fujieid.jap.httpapi.enums.ForBearerTokenEnum;
import com.fujieid.jap.httpapi.enums.HttpMethodEnum;
import com.fujieid.jap.httpapi.strategy.GetTokenFromResponseStrategy;
import com.fujieid.jap.httpapi.strategy.RequestBodyToJapUserStrategy;
import com.xkcoding.json.JsonUtil;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration file for http api authorization module
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
public class HttpApiConfig extends AuthenticateConfig {

    /**
     * Schema for http authorization,defined by third-party system. eg：BASIC、DIGEST、BEARER.
     */
    private AuthSchemaEnum authSchema;

    /**
     * the http method for us to send request to third-party system, which is given by third-party system.
     */
    private HttpMethodEnum httpMethod;

    /**
     * the login url given by the third-party system.
     */
    private String loginUrl;

    /**
     * params/header/body
     */
    private AuthInfoFieldEnum authInfoField;

    /**
     * custom headers will be carried when request third-party system.
     */
    private Map<String, String> customHeaders;

    /**
     * custom params will be carried when request third-party system.
     */
    private Map<String, String> customParams;

    /**
     * custom body will be carried when request third-party system in json format.
     */
    private Map<String, String> customBody;

    /**
     * define this field when authSchema is BEARER
     */
    private String bearerTokenIssueUrl;

    /**
     * if user auth info field is "body", by default, analyze user auth info by json format. ex:
     * {
     * "username":"admin",
     * "password":"123456"
     * }
     * Developer's system should customize requestBodyToJapUserStrategy to get userinfo form request if user auth info is not this format.
     */
    private RequestBodyToJapUserStrategy requestBodyToJapUserStrategy = request -> {
        BufferedReader reader = request.getReader();
        StringBuilder body = new StringBuilder();
        String str = null;
        while ((str = reader.readLine()) != null) {
            body.append(str);
        }
        return JsonUtil.toBean(body.toString(), JapUser.class);
    };

    /**
     * As we know Bearer auth need a token, but how to get this token? we should do a pre-auth to get this token, then store this token
     * in developer's system. when user request for bearer auth, http-api module will query for user's token in our system.
     * This field is used to define how to do pre-auth, how we get the token from third-party serve.
     * When authSchema is BEARER you must define this field.
     */
    private ForBearerTokenEnum forBearerTokenEnum;

    /**
     * When do pre-auth for bearer auth, we get the response from third-party server which contains the token.
     * Use this strategy to extract the token from response body.
     * By default, search for field like "token":"xxxxxxxx" in response.
     */
    private GetTokenFromResponseStrategy getTokenFromResponseStrategy = body -> {
        try {
            int i1 = body.indexOf("\"token\"") + 7;
            int i2 = body.indexOf(':', i1);
            int i3 = body.indexOf("\"", i2) + 1;
            int i4 = body.indexOf("\"", i3 + 1);
            return body.substring(i3, i4);
        } catch (Exception e) {
            return null;
        }
    };

    public HttpApiConfig() {
    }

    public HttpApiConfig(HttpApiConfig httpApiConfig, String loginUrl) {
        this.authSchema = httpApiConfig.authSchema;
        this.httpMethod = httpApiConfig.httpMethod;
        this.loginUrl = loginUrl;
        this.authInfoField = httpApiConfig.authInfoField;
        this.customHeaders = httpApiConfig.customHeaders;
        this.customParams = httpApiConfig.customParams;
        this.customBody = httpApiConfig.customBody;
        this.bearerTokenIssueUrl = httpApiConfig.bearerTokenIssueUrl;
        this.requestBodyToJapUserStrategy = httpApiConfig.requestBodyToJapUserStrategy;
        this.forBearerTokenEnum = httpApiConfig.forBearerTokenEnum;
        this.getTokenFromResponseStrategy = httpApiConfig.getTokenFromResponseStrategy;
    }

    public AuthSchemaEnum getAuthSchema() {
        return authSchema;
    }

    public HttpApiConfig setAuthSchema(AuthSchemaEnum authSchema) {
        this.authSchema = authSchema;
        return this;
    }

    public HttpMethodEnum getHttpMethod() {
        return httpMethod;
    }

    public HttpApiConfig setHttpMethod(HttpMethodEnum httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public HttpApiConfig setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    public AuthInfoFieldEnum getAuthInfoField() {
        return authInfoField;
    }

    public HttpApiConfig setAuthInfoField(AuthInfoFieldEnum authInfoField) {
        this.authInfoField = authInfoField;
        return this;
    }

    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    public HttpApiConfig setCustomHeaders(Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
        return this;
    }

    public RequestBodyToJapUserStrategy getRequestBodyToJapUserStrategy() {
        return requestBodyToJapUserStrategy;
    }

    public HttpApiConfig setRequestBodyToJapUserStrategy(RequestBodyToJapUserStrategy requestBodyToJapUserStrategy) {
        this.requestBodyToJapUserStrategy = requestBodyToJapUserStrategy;
        return this;
    }

    public Map<String, String> getCustomParams() {
        return customParams;
    }

    public HttpApiConfig setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
        return this;
    }

    public Map<String, Object> getCustomParamsObjects() {
        return new HashMap<>(customParams);
    }

    public Map<String, String> getCustomBody() {
        return customBody;
    }

    public HttpApiConfig setCustomBody(Map<String, String> customBody) {
        this.customBody = customBody;
        return this;
    }

    public ForBearerTokenEnum getForBearerTokenEnum() {
        return forBearerTokenEnum;
    }

    public HttpApiConfig setForBearerTokenEnum(ForBearerTokenEnum forBearerTokenEnum) {
        this.forBearerTokenEnum = forBearerTokenEnum;
        return this;
    }

    public GetTokenFromResponseStrategy getGetTokenFromResponseStrategy() {
        return getTokenFromResponseStrategy;
    }

    public HttpApiConfig setGetTokenFromResponseStrategy(GetTokenFromResponseStrategy getTokenFromResponseStrategy) {
        this.getTokenFromResponseStrategy = getTokenFromResponseStrategy;
        return this;
    }

    public String getBearerTokenIssueUrl() {
        return bearerTokenIssueUrl;
    }

    public HttpApiConfig setBearerTokenIssueUrl(String bearerTokenIssueUrl) {
        this.bearerTokenIssueUrl = bearerTokenIssueUrl;
        return this;
    }
}
