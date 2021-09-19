package com.fujieid.jap.httpapi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;

/**
 * used to convert "Authorization Request Header" and "WWW-Authenticate Response Header" between string and object
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
public final class SubjectSerializeUtil {

    /**
     * Deserialize string to object
     *
     * @param str   a <code>String</code> to be deserialized
     * @param clazz The target type to deserialize
     * @return Return an object
     * @throws NoSuchMethodException     no such method
     * @throws IllegalAccessException    illegal access
     * @throws InvocationTargetException invocation target
     * @throws InstantiationException    instantiation
     */
    public static <T> T deserialize(String str, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        str = str.replace("\\", "").replace("\"", "").trim();
        String[] split = str.split(",");
        HashMap<String, String> params = new HashMap<>(8);
        for (String s : split) {
            String[] param = s.split("=");
            String paramKey = param[0].trim();
            String paramValue = param[1].trim();
            params.put(paramKey, paramValue);
        }

        Field[] fields = clazz.getDeclaredFields();

        T t = clazz.getDeclaredConstructor().newInstance();

        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            field.set(t, params.get(name));
        }

        return t;
    }

    /**
     * Serialize object to string
     *
     * @param t Objects that need to be serialized
     * @return return a <code>string</code> after serialization
     * @throws IllegalAccessException illegal access
     */
    public static String serialize(Object t) throws IllegalAccessException {
        Class<?> clazz = t.getClass();
        StringBuilder sb = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        boolean firstParam = true;
        for (Field field : fields) {
            field.setAccessible(true);
            String paramName = field.getName();
            String paramVal = ((String) field.get(t));
            if (Objects.isNull(paramVal)) {
                continue;
            }
            if (!firstParam) {
                sb.append(",");
            }
            firstParam = false;
            sb.append(paramName).append("=\"").append(paramVal).append("\"");
        }
        return sb.toString();
    }
}
