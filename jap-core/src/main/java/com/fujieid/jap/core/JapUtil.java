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
package com.fujieid.jap.core;

import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.core.exception.JapException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The tool class of Jap only provides static methods common to all modules
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapUtil {

    private static final String REDIRECT_ERROR = "JAP failed to redirect via HttpServletResponse.";

    public static void redirect(String url, HttpServletResponse response) {
        redirect(url, REDIRECT_ERROR, response);
    }

    public static void redirect(String url, String errorMessage, HttpServletResponse response) {
        try {
            response.sendRedirect(url);
        } catch (IOException ex) {
            throw new JapException(errorMessage, ex);
        }
    }

    public static String convertToStr(Object o) {
        if (ObjectUtil.isNull(o)) {
            return null;
        }
        if (o instanceof String) {
            return String.valueOf(o);
        }
        return o.toString();
    }

    public static Integer convertToInt(Object o) {
        if (ObjectUtil.isNull(o)) {
            return null;
        }
        if (o instanceof String) {
            return Integer.parseInt(String.valueOf(o));
        }
        if (o instanceof Integer) {
            return (Integer) o;
        }
        throw new ClassCastException(o + " cannot be converted to Integer type");
    }
}
