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
package com.fujieid.jap.ids.util;

import cn.hutool.core.util.URLUtil;
import com.xkcoding.json.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class ObjectUtils {

    /**
     * If the {@code str} does not end in {@code suffix}, then {@code suffix} is appended after {@code str};
     * If {@code suffix} is already included, return directly to {@code str}
     *
     * @param str    str
     * @param suffix Content to be added
     * @return String
     */
    public static String appendIfNotEndWith(String str, String suffix) {
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(suffix)) {
            return str;
        }
        return str.endsWith(suffix) ? str : str + suffix;
    }

    /**
     * string to map, str format is{@code xxx=xxx&xxx=xxx}
     *
     * @param input The string to be converted
     * @return map
     */
    public static Map<String, String> parseStringToMap(String input) {
        Map<String, String> res = null;
        if (input.contains("&")) {
            String[] fields = input.split("&");
            res = new HashMap<>((int) (fields.length / 0.75 + 1));
            for (String field : fields) {
                if (field.contains("=")) {
                    String[] keyValue = field.split("=");
                    res.put(keyValue[0], keyValue.length == 2 ? keyValue[1] : null);
                }
            }
        } else if (input.contains("=")) {
            String[] keyValue = input.split("=");
            res = new HashMap<>((int) (keyValue.length / 0.75 + 1));
            res.put(keyValue[0], keyValue.length == 2 ? keyValue[1] : null);
        } else {
            res = new HashMap<>(0);
        }
        return res;
    }

    /**
     * Map to string, the format of the converted string is {@code xxx=xxx&xxx=xxx}
     *
     * @param params Map to be converted
     * @param encode Whether to encode the value of the map
     * @return String
     */
    public static String parseMapToString(Map<String, String> params, boolean encode) {
        if (null == params || params.isEmpty()) {
            return "";
        }
        List<String> paramList = new ArrayList<>();
        params.forEach((k, v) -> {
            if (null == v) {
                paramList.add(k + "=");
            } else {
                paramList.add(k + "=" + (encode ? URLUtil.encode(v) : v));
            }
        });
        return String.join("&", paramList);
    }
}
