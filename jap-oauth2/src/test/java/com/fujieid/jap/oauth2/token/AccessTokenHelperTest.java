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
package com.fujieid.jap.oauth2.token;

import cn.hutool.core.io.IORuntimeException;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
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
public class AccessTokenHelperTest {

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTokenNullOAuthConfig() {
        Assert.assertThrows(JapOauth2Exception.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, null));
    }

    @Test
    public void getTokenEmptyOAuthConfig() {
        Assert.assertThrows(JapOauth2Exception.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()));
    }

    @Test
    public void getTokenCodeResponseType() {
        Assert.assertThrows(JapOauth2Exception.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setResponseType(Oauth2ResponseType.code)));
    }

    @Test
    public void getTokenCodeResponseTypeDoNotCheckState() {
        // Http url must be not blank!
        Assert.assertThrows(IllegalArgumentException.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setVerifyState(false)
            .setResponseType(Oauth2ResponseType.code)));
    }

    @Test
    public void getTokenCodeResponseTypeErrorTokenUrl() {
        // UnknownHostException: setTokenUrl
        Assert.assertThrows(IORuntimeException.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setVerifyState(false)
            .setResponseType(Oauth2ResponseType.code)
            .setEnablePkce(true)
            .setCallbackUrl("setCallbackUrl")
            .setTokenUrl("setTokenUrl")));
    }

    @Test
    public void getTokenTokenResponseType() {
        // Oauth2Strategy failed to get AccessToken.
        Assert.assertThrows(JapOauth2Exception.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setResponseType(Oauth2ResponseType.token)));
    }

    @Test
    public void getTokenPasswordGrantTypeNullTokenUrl() {
        // Http url must be not blank!
        Assert.assertThrows(IllegalArgumentException.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setGrantType(Oauth2GrantType.password)
            .setScopes(new String[]{"read"})));
    }

    @Test
    public void getTokenPasswordGrantTypeErrorTokenUrl() {
        // UnknownHostException: setTokenUrl
        Assert.assertThrows(IORuntimeException.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setGrantType(Oauth2GrantType.password)
            .setScopes(new String[]{"read"})
            .setTokenUrl("setTokenUrl")));
    }

    @Test
    public void getTokenClientCredentialsGrantTypeErrorTokenUrl() {
        // UnknownHostException: setTokenUrl
        Assert.assertThrows(IORuntimeException.class, () -> AccessTokenHelper.getToken(httpServletRequestMock, new OAuthConfig()
            .setGrantType(Oauth2GrantType.client_credentials)
            .setScopes(new String[]{"read"})
            .setTokenUrl("setTokenUrl")));
    }
}
