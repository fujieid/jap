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
package com.fujieid.jap.sso;

import com.baomidou.kisso.security.token.SSOToken;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.adapter.jakarta.JakartaRequestAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

public class JapSsoUtilTest {

    public JapHttpRequest request;
    @Mock
    private HttpServletRequest httpServletRequestMock;
    @Mock
    private HttpSession httpsSessionMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        // Arrange
        when(httpServletRequestMock.getSession()).thenReturn(httpsSessionMock);
        this.request = new JakartaRequestAdapter(httpServletRequestMock);
    }

    @Test
    public void createSsoToken() {
        when(httpServletRequestMock.getHeader("x-forwarded-for")).thenReturn("127.0.0.1");
        when(httpServletRequestMock.getHeader("user-agent")).thenReturn("ua");
        SSOToken ssoToken = JapSsoUtil.createSsoToken("userId", "userName", request);
        System.out.println(ssoToken);
        Assert.assertNotNull(ssoToken);
    }

    @Test
    public void createToken() {
        when(httpServletRequestMock.getHeader("x-forwarded-for")).thenReturn("127.0.0.1");
        when(httpServletRequestMock.getHeader("user-agent")).thenReturn("ua");
        String token = JapSsoUtil.createToken("userId", "userName", request);
        System.out.println(token);
        Assert.assertNotNull(token);
    }

    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ1c2VySWQiLCJpcCI6IjEyNy4wLjAuMSIsImlzcyI6InVzZXJOYW1lIiwidWEiOiI5ZjRkNyIsImlhdCI6MTYxNDU3MzkyM30.A4Z6LSJoIu_scS6L1J13GaSgmEpQCxiRqpHDByIZ_S34cTFnamEKozPvRJZqFoGQbKFWsxOmTH7wepaM9ru5Aw";
        SSOToken ssoToken = JapSsoUtil.parseToken(token);
        System.out.println(ssoToken.getClaims());
        Assert.assertEquals("{jti=userId, ip=127.0.0.1, iss=userName, ua=9f4d7, iat=1614573923}", ssoToken.getClaims().toString());
    }

    @Test
    public void parseInvalidToken() {
        String token = "InvalidToken";
        SSOToken ssoToken = JapSsoUtil.parseToken(token);
        Assert.assertNull(ssoToken);
    }
}
