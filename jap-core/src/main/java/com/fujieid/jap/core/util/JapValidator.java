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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fujieid.jap.core.annotation.NotEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class JapValidator {

    public static List<String> validate(Object o) {
        List<String> errors = new ArrayList<>();
        if (null == o) {
            return errors;
        }
        Field[] fields = ReflectUtil.getFields(o.getClass());
        if (ArrayUtil.isEmpty(fields)) {
            return errors;
        }
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            if (ArrayUtil.isEmpty(annotations)) {
                return errors;
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof NotEmpty) {
                    Object object = ReflectUtil.getFieldValue(o, field);
                    if (ObjectUtil.isEmpty(object)) {
                        errors.add(((NotEmpty) annotation).message());
                    }
                }
            }
        }
        return errors;
    }
}
