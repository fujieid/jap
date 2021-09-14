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
package com.fujieid.jap.core.util;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.adapter.jakarta.JakartaRequestAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class JapUtilTest {
    @Mock
    public HttpServletRequest httpServletRequestMock;

    public JapHttpRequest request;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.request = new JakartaRequestAdapter(httpServletRequestMock);
    }

    @Test
    public void createToken() {
        when(httpServletRequestMock.getHeader("x-forwarded-for")).thenReturn("127.0.0.1");
        when(httpServletRequestMock.getHeader("user-agent")).thenReturn("ua");
        String token = JapUtil.createToken(new JapUser()
            .setUserId("11111")
            .setUsername("username"), request);
        Assert.assertNotNull(token);
    }
}
