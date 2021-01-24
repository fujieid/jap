package com.fujieid.jap.simple;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.fujieid.jap.core.JapConst.DEFAULT_DELIMITER;

/**
 * @author harrylee (harryleexyz(a)qq.com)
 * @version 1.0.0
 * @date 2021/1/24 19:12
 * @since 1.0.0
 */
public class RememberMeDetailsUtils {
    /**
     * Encrypted acquisition instance.
     *
     * @param simpleConfig config
     * @param username     username
     * @return
     */
    public static RememberMeDetails encode(SimpleConfig simpleConfig, String username) {
        long expiryTime = System.currentTimeMillis() + simpleConfig.getRememberMeCookieExpire();
        // username:tokenExpiryTime
        String md5Data = username + DEFAULT_DELIMITER + expiryTime;
        String md5Key = simpleConfig.getCredentialEncrypt().digestHex16(md5Data);
        // username:tokenExpiryTime:key
        String base64Data = md5Data + DEFAULT_DELIMITER + md5Key;
        return new RememberMeDetails()
            .setUsername(username)
            .setExpiryTime(expiryTime)
            .setEncodeValue(new String(Base64.getEncoder().encode(base64Data.getBytes(StandardCharsets.UTF_8))));
    }

    /**
     * Decryption acquisition instance.
     *
     * @param simpleConfig config
     * @param cookieValue  cookie value
     * @return
     */
    public static RememberMeDetails decode(SimpleConfig simpleConfig, String cookieValue) {
        if (!simpleConfig.isEnableRememberMe()) {
            return null;
        }
        String base64DecodeValue;
        try {
            base64DecodeValue = new String(Base64.getDecoder().decode(cookieValue.getBytes(StandardCharsets.UTF_8)));
        } catch (RuntimeException e) {
            return null;
        }
        String[] base64DecodeValueSplitArray = StrUtil.splitToArray(base64DecodeValue, DEFAULT_DELIMITER);
        // Check and validate keys
        if (base64DecodeValueSplitArray.length > 2) {
            String username = base64DecodeValueSplitArray[0];
            long expiryTime;
            try {
                expiryTime = Long.parseLong(base64DecodeValueSplitArray[1]);
            } catch (RuntimeException e) {
                return null;
            }
            // overdue
            if (expiryTime < System.currentTimeMillis()) {
                return null;
            }
            // username:tokenExpiryTime
            String md5Data = username + DEFAULT_DELIMITER + expiryTime;
            String md5Key = simpleConfig.getCredentialEncrypt().digestHex16(md5Data);
            // Check pass returns
            if (ObjectUtil.equal(md5Key, base64DecodeValueSplitArray[2])) {
                return new RememberMeDetails()
                    .setUsername(username)
                    .setExpiryTime(expiryTime)
                    .setEncodeValue(cookieValue);
            }
        }
        return null;
    }
}
