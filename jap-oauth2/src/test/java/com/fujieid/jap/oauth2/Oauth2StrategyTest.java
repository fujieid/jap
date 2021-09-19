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

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.http.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.jakarta.JakartaResponseAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * unit test
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Oauth2StrategyTest {

    public JapHttpRequest request;
    public JapHttpResponse response;
    @Mock
    private HttpServletRequest httpServletRequestMock;
    @Mock
    private HttpServletResponse httpServletResponseMock;
    @Mock
    private HttpSession httpsSessionMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        // Arrange
        when(httpServletRequestMock.getSession()).thenReturn(httpsSessionMock);
        this.request = new JakartaRequestAdapter(httpServletRequestMock);
        this.response = new JakartaResponseAdapter(httpServletResponseMock);
    }

    @Test
    public void constructor() {
        JapUserService japUserService = getJapUserService();
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig(), new JapCache() {
            @Override
            public void set(String key, Serializable value) {

            }

            @Override
            public void set(String key, Serializable value, long timeout) {

            }

            @Override
            public Serializable get(String key) {
                return null;
            }

            @Override
            public boolean containsKey(String key) {
                return false;
            }

            /**
             * Delete the key from the cache
             *
             * @param key Cache key
             */
            @Override
            public void removeKey(String key) {

            }
        });
    }

    @Test
    public void authenticateNullConfig() {
        JapUserService japUserService = getJapUserService();
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig());

        JapResponse response = oauth2Strategy.authenticate(null, this.request, this.response);
        Assert.assertEquals(1005, response.getCode());
    }

    @Test
    public void authenticateNotOAuthConfig() {
        JapUserService japUserService = getJapUserService();
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig());

        JapResponse response = oauth2Strategy.authenticate(new NotOAuthConfig(), this.request, this.response);
        Assert.assertEquals(500, response.getCode());
    }

    @Test
    public void authenticate() {
        JapUserService japUserService = getJapUserService();
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig());
        // Redirect to authorization url
        oauth2Strategy.authenticate(new OAuthConfig()
            .setTokenUrl("TokenUrl")
            .setResponseType(Oauth2ResponseType.TOKEN)
            .setClientSecret("ClientSecret")
            .setClientId("ClientId")
            .setAuthorizationUrl("AuthorizationUrl")
            .setUserinfoUrl("UserinfoUrl"), this.request, this.response);
    }

    private JapUserService getJapUserService() {
        return new JapUserService() {
            /**
             * Get user information in the current system by social platform and social user id
             * <p>
             * It is suitable for the {@code jap-social} module
             *
             * @param platform social platform，refer to {@code me.zhyd.oauth.config.AuthSource#getName()}
             * @param uid      social user id
             * @return JapUser
             */
            @Override
            public JapUser getByPlatformAndUid(String platform, String uid) {
                return new JapUser();
            }

            /**
             * Save the oauth login user information to the database and return JapUser
             * <p>
             * It is suitable for the {@code jap-oauth2} module
             *
             * @param platform  oauth2 platform name
             * @param userInfo  The basic user information returned by the OAuth platform
             * @param tokenInfo The token information returned by the OAuth platform, developers can store tokens
             *                  , type {@code com.fujieid.jap.oauth2.helper.AccessToken}
             * @return When saving successfully, return {@code JapUser}, otherwise return {@code null}
             */
            @Override
            public JapUser createAndGetOauth2User(String platform, Map<String, Object> userInfo, Object tokenInfo) {
                return new JapUser();
            }
        };
    }

    public static class NotOAuthConfig extends AuthenticateConfig {

    }

}
