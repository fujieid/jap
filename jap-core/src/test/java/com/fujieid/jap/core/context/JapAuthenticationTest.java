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
package com.fujieid.jap.core.context;

import com.fujieid.jap.core.JapConst;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.cache.JapLocalCache;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JapAuthenticationTest {
    @Mock
    public HttpServletRequest httpServletRequestMock;
    @Mock
    public HttpServletResponse httpServletResponseMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getContext() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);
        Assert.assertNotNull(JapAuthentication.getContext());
    }

    @Test
    public void setContext() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);
    }

    @Test
    public void getNullUser() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        JapUser japUser = JapAuthentication.getContext().getUserStore().get(httpServletRequestMock, httpServletResponseMock);
        Assert.assertNull(japUser);
    }

    @Test
    public void getUser() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        JapAuthentication.getContext().getUserStore().save(httpServletRequestMock, httpServletResponseMock, new JapUser());
        JapUser japUser = JapAuthentication.getContext().getUserStore().get(httpServletRequestMock, httpServletResponseMock);
        Assert.assertNotNull(japUser);
    }

    @Test
    public void getUserNullStore() {
        JapUserStore japUserStore = null;
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        JapUser japUser = JapAuthentication.getUser(httpServletRequestMock, httpServletResponseMock);
        Assert.assertNull(japUser);
    }

    @Test
    public void checkNullUser() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        JapResponse response = JapAuthentication.checkUser(httpServletRequestMock, httpServletResponseMock);
        Assert.assertEquals(response.getCode(), 401);
    }

    @Test
    public void checkUser() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        JapAuthentication.getContext().getUserStore().save(httpServletRequestMock, httpServletResponseMock, new JapUser());
        JapResponse response = JapAuthentication.checkUser(httpServletRequestMock, httpServletResponseMock);
        Assert.assertEquals(response.getCode(), 200);
    }

    @Test
    public void checkTokenNullContext() {
        Map<String, Object> map = JapAuthentication.checkToken("aaa");
        Assert.assertNull(map);
    }

    @Test
    public void checkTokenNullCache() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = null;
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        Map<String, Object> map = JapAuthentication.checkToken("aaa");
        Assert.assertNull(map);
    }

    @Test
    public void checkTokenNullToken() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        Map<String, Object> map = JapAuthentication.checkToken(null);
        Assert.assertNull(map);
    }

    @Test
    public void checkTokenNotInCache() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);
        String cacheKey = JapConst.USER_TOKEN_KEY.concat("1111");
        japCache.removeKey(cacheKey);
        Map<String, Object> map = JapAuthentication.checkToken("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxMTExIiwiaXAiOiIxOTIuMTY4LjEuMTAzIiwiaXNzIjoiamFwIiwidWEiOiJiM2VmOSIsImlhdCI6MTYxNDY3NjA2N30.MK6CBJR98y6UnRBy2coHXrxNJU4N4bZIA05oCgkaNYODdfSRwXhUEqV-OqYsushOxNmUYH0Lp6sKAtrBip0yCw");
        System.out.println(map);
        Assert.assertNull(map);
    }

    @Test
    public void checkToken() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxMTExIiwiaXAiOiIxOTIuMTY4LjEuMTAzIiwiaXNzIjoiamFwIiwidWEiOiJiM2VmOSIsImlhdCI6MTYxNDY3NjA2N30.MK6CBJR98y6UnRBy2coHXrxNJU4N4bZIA05oCgkaNYODdfSRwXhUEqV-OqYsushOxNmUYH0Lp6sKAtrBip0yCw";
        String cacheKey = JapConst.USER_TOKEN_KEY.concat("1111");
        japCache.set(cacheKey, token);
        Map<String, Object> map = JapAuthentication.checkToken(token);
        System.out.println(map);
        // token 已经过期，会返回 null
        Assert.assertNull(map);
//        Assert.assertEquals("{jti=1111, ip=192.168.1.103, iss=jap, ua=b3ef9, iat=1614676067}", map.toString());
    }

    @Test
    public void logout() {
        JapUserStore japUserStore = new JapUserStoreTest();
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        boolean result = JapAuthentication.logout(httpServletRequestMock, httpServletResponseMock);
        Assert.assertTrue(result);
    }

    @Test
    public void logoutNullStore() {
        JapUserStore japUserStore = null;
        JapCache japCache = new JapLocalCache();
        JapConfig japConfig = new JapConfig();
        JapContext japContext = new JapContext(japUserStore, japCache, japConfig);
        JapAuthentication.setContext(japContext);

        boolean result = JapAuthentication.logout(httpServletRequestMock, httpServletResponseMock);
        Assert.assertFalse(result);
    }

    static class JapUserStoreTest implements JapUserStore {
        Map<String, Object> STORE = new HashMap<>();
        String USER_KEY = "user";

        /**
         * Login completed, save user information to the cache
         *
         * @param request  current HTTP request
         * @param response current HTTP response
         * @param japUser  User information after successful login
         * @return JapUser
         */
        @Override
        public JapUser save(HttpServletRequest request, HttpServletResponse response, JapUser japUser) {
            STORE.put(USER_KEY, japUser);
            return japUser;
        }

        /**
         * Clear user information from cache
         *
         * @param request  current HTTP request
         * @param response current HTTP response
         */
        @Override
        public void remove(HttpServletRequest request, HttpServletResponse response) {
            STORE.remove(USER_KEY);
        }

        /**
         * Get the login user information from the cache, return {@code JapUser} if it exists,
         * return {@code null} if it is not logged in or the login has expired
         *
         * @param request  current HTTP request
         * @param response current HTTP response
         * @return JapUser
         */
        @Override
        public JapUser get(HttpServletRequest request, HttpServletResponse response) {
            return (JapUser) STORE.get(USER_KEY);
        }
    }
}
