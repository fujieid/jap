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
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.sso.JapSsoUtil;

/**
 * The tool class of Jap only provides static methods common to all modules
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapUtil extends com.xkcoding.json.util.ObjectUtil {

    private static final String REDIRECT_ERROR = "JAP failed to redirect via HttpServletResponse.";

    @Deprecated
    public static void redirect(String url, JapHttpResponse response) {
        redirect(url, REDIRECT_ERROR, response);
    }

    @Deprecated
    public static void redirect(String url, String errorMessage, JapHttpResponse response) {
        response.redirect(url);
    }

    public static String createToken(JapUser japUser, JapHttpRequest request) {
        return JapSsoUtil.createToken(japUser.getUserId(), japUser.getUsername(), request);
    }
}
