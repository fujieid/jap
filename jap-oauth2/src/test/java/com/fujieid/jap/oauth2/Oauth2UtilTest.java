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
package com.fujieid.jap.oauth2;

import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.cache.JapCacheContextHolder;
import com.fujieid.jap.core.cache.JapLocalCache;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.oauth2.pkce.PkceCodeChallengeMethod;
import com.xkcoding.json.util.Kv;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

/**
 * unit test
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Oauth2UtilTest {

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateCodeChallengeCodeChallengeMethodIsS256() {
        String s256Challenge = Oauth2Util.generateCodeChallenge(PkceCodeChallengeMethod.S256, "asdasdasdasd");
        Assert.assertEquals("ZrETKgFzkQsB7joV705pWDu_L38eRGLJnvvhuatb-Ag", s256Challenge);
    }

    @Test
    public void generateCodeChallengeCodeChallengeMethodIsPlain() {
        String plainChallenge = Oauth2Util.generateCodeChallenge(PkceCodeChallengeMethod.PLAIN, "asdasdasdasd");
        Assert.assertEquals("asdasdasdasd", plainChallenge);
    }

    @Test
    public void generateCodeVerifier() {
        String verifier = Oauth2Util.generateCodeVerifier();
        System.out.println(verifier);
        Assert.assertEquals(67, verifier.length());
    }

    @Test
    public void checkOauthResponseNoError() {
        Kv kv = new Kv();
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg);
    }

    @Test
    public void checkOauthResponseHasEmptyError() {
        Kv kv = new Kv();
        kv.put("error", "");
        kv.put("error_description", "invalid_request_description");
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg);
    }

    @Test
    public void checkOauthResponseHasNullError() {
        Kv kv = new Kv();
        kv.put("error", null);
        kv.put("error_description", "invalid_request_description");
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg);
    }

    @Test
    public void checkOauthResponseHasError() {
        Kv kv = new Kv();
        kv.put("error", "invalid_request");
        kv.put("error_description", "invalid_request_description");
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg));
    }

    @Test
    public void checkOauthCallbackRequestNullError() {
        Oauth2Util.checkOauthCallbackRequest(null, null, null);
    }

    @Test
    public void checkOauthCallbackRequestEmptyError() {
        Oauth2Util.checkOauthCallbackRequest("", null, null);
    }

    @Test
    public void checkOauthCallbackRequestHasError() {
        String requestErrorDescParam = "requestErrorDescParam";
        String bizErrorMsg = "bizErrorMsg";
        String requestErrorParam = "requestErrorParam";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthCallbackRequest(requestErrorParam, requestErrorDescParam, bizErrorMsg));
    }

    @Test
    public void checkOauthCallbackRequestHasErrorAndEmptyBizErrorMsg() {
        String requestErrorDescParam = "requestErrorDescParam";
        String bizErrorMsg = "";
        String requestErrorParam = "requestErrorParam";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthCallbackRequest(requestErrorParam, requestErrorDescParam, bizErrorMsg));
    }

    @Test
    public void checkOauthCallbackRequestHasErrorAndNullBizErrorMsg() {
        String requestErrorDescParam = "requestErrorDescParam";
        String bizErrorMsg = null;
        String requestErrorParam = "requestErrorParam";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthCallbackRequest(requestErrorParam, requestErrorDescParam, bizErrorMsg));
    }

    @Test
    public void checkStateNullStateAndNullClientId() {
        boolean verifyState = true;
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkState(null, null, verifyState));
    }

    @Test
    public void checkStateNoVerify() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = false;
        Oauth2Util.checkState(state, clientId, verifyState);
    }

    @Test
    public void checkStateCacheExists() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = true;
        JapCache cache = new JapLocalCache();
        cache.set(Oauth2Const.STATE_CACHE_KEY.concat(clientId), state);
        JapCacheContextHolder.enable(cache);
        Oauth2Util.checkState(state, clientId, verifyState);
    }

    @Test
    public void checkStateCacheDoesNotExist() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = true;
        JapCache cache = new JapLocalCache();
        cache.set(Oauth2Const.STATE_CACHE_KEY.concat(clientId), "11");
        JapCacheContextHolder.enable(cache);
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkState(state, clientId, verifyState));
    }


    @Test
    public void checkOauthConfigNullTokenUrl() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()));
    }


    @Test
    public void checkOauthConfigHasTokenUrlAndNullGrantType() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")));
    }


    @Test
    public void checkOauthConfigCodeResponseTypeAndGrantTypeIsNotAuthorizationCode() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.code)
            .setGrantType(Oauth2GrantType.password)));
    }


    @Test
    public void checkOauthConfigCodeResponseTypeAndClientSecretIsNullWhenPkceIsNotEnabled() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.code)
            .setGrantType(Oauth2GrantType.authorization_code)
            .setClientSecret(null)));
    }


    @Test
    public void checkOauthConfigCodeResponseTypeAndClientSecretIsEmptyWhenPkceIsNotEnabled() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.code)
            .setGrantType(Oauth2GrantType.authorization_code)
            .setClientSecret("")));
    }


    @Test
    public void checkOauthConfigCodeResponseTypeAndClientSecretIsNullWhenPkceIsEnabled() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.code)
            .setGrantType(Oauth2GrantType.authorization_code)
            .setEnablePkce(true)
            .setClientSecret(null)));
    }


    @Test
    public void checkOauthConfigCodeResponseTypeAndClientSecretIsEmptyWhenPkceIsEnabled() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.code)
            .setGrantType(Oauth2GrantType.authorization_code)
            .setEnablePkce(true)
            .setClientSecret("")));
    }


    @Test
    public void checkOauthConfigCodeResponseTypeAndClientSecretIsNotEmptyWhenPkceIsEnabled() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.code)
            .setGrantType(Oauth2GrantType.authorization_code)
            .setEnablePkce(true)
            .setClientSecret("ClientSecret")));
    }


    @Test
    public void checkOauthConfigTokenResponseTypeAndClientSecretIsEmpty() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("")));
    }


    @Test
    public void checkOauthConfigTokenResponseTypeAndClientSecretIsNull() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret(null)));
    }


    @Test
    public void checkOauthConfigCodeOrTokenResponseTypeAndClientIdIsNull() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId(null)));
    }

    @Test
    public void checkOauthConfigCodeOrTokenResponseTypeAndClientIdIsEmpty() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId("")));
    }

    @Test
    public void checkOauthConfigCodeOrTokenResponseTypeAndAuthorizationUrlIsEmpty() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId("ClientId")
            .setAuthorizationUrl("")));
    }

    @Test
    public void checkOauthConfigCodeOrTokenResponseTypeAndAuthorizationUrlIsNull() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId("ClientId")
            .setAuthorizationUrl(null)));
    }

    @Test
    public void checkOauthConfigCodeOrTokenResponseTypeAndUserinfoUrlIsNull() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId("ClientId")
            .setAuthorizationUrl("AuthorizationUrl")
            .setUserinfoUrl(null)));
    }

    @Test
    public void checkOauthConfigCodeOrTokenResponseTypeAndUserinfoUrlIsEmpty() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId("ClientId")
            .setAuthorizationUrl("AuthorizationUrl")
            .setUserinfoUrl("")));
    }

    @Test
    public void checkOauthConfigCodeOrTokenResponseType() {
        Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.token)
            .setClientSecret("ClientSecret")
            .setClientId("ClientId")
            .setAuthorizationUrl("AuthorizationUrl")
            .setUserinfoUrl("UserinfoUrl"));
    }

    @Test
    public void checkOauthConfigClientCredentialsGrantType() {
        Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setGrantType(Oauth2GrantType.client_credentials));
    }

    @Test
    public void checkOauthConfigPasswordGrantTypeAndNullUsernameAndPassword() {
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setGrantType(Oauth2GrantType.password)));
    }

    @Test
    public void checkOauthConfigPasswordGrantTypeAndHasUsernameAndPassword() {
        Oauth2Util.checkOauthConfig(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setGrantType(Oauth2GrantType.password)
            .setUsername("username")
            .setPassword("password"));
    }

    @Test
    public void isCallbackCodeResponseType() {
        boolean res = Oauth2Util.isCallback(httpServletRequestMock, new OAuthConfig()
            .setResponseType(Oauth2ResponseType.code));
        Assert.assertFalse(res);
    }

    @Test
    public void isCallbackTokenResponseType() {
        boolean res = Oauth2Util.isCallback(httpServletRequestMock, new OAuthConfig()
            .setResponseType(Oauth2ResponseType.token));
        Assert.assertFalse(res);
    }

    @Test
    public void isCallbackNoneResponseType() {
        boolean res = Oauth2Util.isCallback(httpServletRequestMock, new OAuthConfig()
            .setResponseType(Oauth2ResponseType.none));
        Assert.assertFalse(res);
    }
}
