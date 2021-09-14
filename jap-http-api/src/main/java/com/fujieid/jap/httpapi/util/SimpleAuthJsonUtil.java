package com.fujieid.jap.httpapi.util;

import com.fujieid.jap.core.JapUser;
import java.util.Map;

/**
 *  Convert between json and auth info
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SimpleAuthJsonUtil {

    public static String getJsonStrByParams(Map<String,String> params){
        return getJsonStrByJapUserAndParams(null,params);
    }

    public static String getJsonStrByJapUserAndParams(JapUser japUser, Map<String,String> params){
        if(japUser==null&&params==null){
            return null;
        }
        StringBuilder str = new StringBuilder();
        str.append("{");

        if(japUser!=null){
            str.append("\"username\":\"").append(japUser.getUsername()).append("\",\"password:\"").append(japUser.getPassword()).append("\",");
        }

        if(params!=null){
            params.forEach((key, value) -> str.append("\"").append(key).append("\":\"").append(value).append("\","));
        }

        if(str.charAt(str.length()-1)==','){
            str.deleteCharAt(str.length()-1);
        }

        str.append("}");
        return str.toString();
    }
}
