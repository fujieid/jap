package com.fujieid.jap.httpapi.util;


import cn.hutool.core.lang.UUID;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DigestUtil {

    private static final LongAdder LONG_ADDER = new LongAdder();

    public static String getCnonce(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,16);
    }

    public static String getNc(){
        LONG_ADDER.increment();
        return LONG_ADDER.toString();
    }

    public static String getResponseByQop(String ha1, String nonce, String nc, String cnonce, String qop, String ha2) {
        if(qop!=null&&(qop.contains("auth")||qop.contains("auth-init"))){
            return DigestMD5Util.encode(ha1,nonce,nc,cnonce,qop,ha2);
        }else{
            return DigestMD5Util.encode(ha1,nonce,ha2);
        }
    }

    public static String getHa2ByQop(String qop, String method, String digestUri) {
        if(qop==null||!qop.contains("auth-init")){
            return DigestMD5Util.encode(method,digestUri);
        }else{
            // TODO Support "auth-init" ha2 compute method
            return null;
        }
    }
}
