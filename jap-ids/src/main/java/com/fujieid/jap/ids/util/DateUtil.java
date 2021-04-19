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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    /**
     * Convert timestamp to LocalDateTime
     *
     * @param second     Long type timestamp, in seconds
     * @param zoneOffset Time zone, default is {@code +8}
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime ofEpochSecond(Long second, ZoneOffset zoneOffset) {
        if (zoneOffset == null) {
            return LocalDateTime.ofEpochSecond(second, 0, ZoneOffset.ofHours(8));
        } else {
            return LocalDateTime.ofEpochSecond(second, 0, zoneOffset);
        }
    }

    /**
     * Get the current Date
     *
     * @return LocalDateTime
     */
    public static LocalDateTime nowDate() {
        return LocalDateTime.now();
    }
}
