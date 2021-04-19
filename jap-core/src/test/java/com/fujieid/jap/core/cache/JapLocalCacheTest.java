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
package com.fujieid.jap.core.cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * unit test
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapLocalCacheTest {

    @Test
    public void set() {
        JapCache japCache = new JapLocalCache();
        japCache.set("key", "value");
        String value = (String) japCache.get("key");
        Assert.assertEquals("value", value);
    }

    @Test
    public void set2() {
        JapCache japCache = new JapLocalCache();
        japCache.set("key", "value", 10000);
        String value = (String) japCache.get("key");
        Assert.assertEquals("value", value);
    }

    @Test
    public void getByNullKey() {
        JapCache japCache = new JapLocalCache();
        String res = (String) japCache.get(null);
        Assert.assertNull(res);
    }

    @Test
    public void getByEmptyKey() {
        JapCache japCache = new JapLocalCache();
        String res = (String) japCache.get("");
        Assert.assertNull(res);
    }

    @Test
    public void getByNotEmptyKey() {
        JapCache japCache = new JapLocalCache();
        String res = (String) japCache.get("ke1y");
        System.out.println(res);
        Assert.assertNull(res);
    }

    @Test
    public void containsNullKey() {
        JapCache japCache = new JapLocalCache();
        boolean containsKey = japCache.containsKey(null);
        Assert.assertFalse(containsKey);
    }

    @Test
    public void containsEmptyKey() {
        JapCache japCache = new JapLocalCache();
        boolean containsKey = japCache.containsKey("");
        Assert.assertFalse(containsKey);
    }

    @Test
    public void containsNotEmptyKey() {
        JapCache japCache = new JapLocalCache();
        boolean containsKey = japCache.containsKey("Key");
        Assert.assertFalse(containsKey);
    }
}
