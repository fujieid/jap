package com.fujieid.jap.httpapi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Splice strings by ":", and encode using md5. eg："MD5(MD5(username:realm:password):nonce:cnonce)"
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DigestMD5Util {

    private final static String[] HEX_ARRAY = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String encode(String... strs){
        StringBuilder stringBuffer = new StringBuilder();
        boolean isFirst = true;
        for(String str:strs){
            if(isFirst){
                stringBuffer.append(str);
                isFirst=false;
            }else{
                stringBuffer.append(":").append(str);
            }
        }

        // Create an information summary with MD5 algorithm
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = md.digest(stringBuffer.toString().getBytes());
        return byteArrayToHex(bytes);

    }
    private static String byteArrayToHex(byte[] b){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHex(b[i]));
        }
        return sb.toString();
    }

    public static String byteToHex(byte b) {
        int n = b;
        if (n < 0){
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_ARRAY[d1]+ HEX_ARRAY[d2];
    }
}
