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
package com.fujieid.jap.core.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.2
 */
public class JapServiceLoader {

    public static <T> List<T> load(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (null == clazz) {
            return list;
        }
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        for (T t : serviceLoader) {
            list.add(t);
        }
        return list;
    }

    public static <T> T loadFirst(Class<T> clazz) {
        List<T> list = load(clazz);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
