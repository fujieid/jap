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
package com.fujieid.jap.core.store;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.exception.JapException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Store and obtain the {@code JapUserStore}, and provide simple operation for users.
 * <p>
 * Currently, Mixed scenarios of single sign on and non single sign on are not supported for the time being, such as:
 * The system uses a variety of login methods, such as {@code jap-simple} and {@code jap-oauth2},
 * but it requires that {@code jap-simple} support Single sign on of {@code jap-oauth2} does not support Single sign on.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-01-21 10:25
 * @since 1.0.0
 */
public class JapUserStoreContextHolder {
    private static final Log log = LogFactory.get();

    private static JapUserStore currentJapUserStore;

    public static void enable(JapUserStore japUserStore) {
        if (null == japUserStore) {
            throw new JapException("JapUserStore cannot be null.");
        }
        currentJapUserStore = japUserStore;
    }

    public static JapUserStore getUserStore() {
        if (null == currentJapUserStore) {
            log.warn("JapUserStore has not been initialized yet.");
            return null;
        }
        return currentJapUserStore;
    }

    public static JapUser getStoreUser(HttpServletRequest request, HttpServletResponse response) {
        JapUserStore japUserStore = getUserStore();
        return null == japUserStore ? null : japUserStore.get(request, response);
    }

    public static void removeStoreUser(HttpServletRequest request, HttpServletResponse response) {
        JapUserStore japUserStore = getUserStore();
        if (null == japUserStore) {
            return;
        }
        japUserStore.remove(request, response);
    }
}
