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
package com.fujieid.jap.simple;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.http.JapHttpRequest;

import java.nio.charset.StandardCharsets;

import static com.fujieid.jap.core.JapConst.DEFAULT_DELIMITER;

/**
 * @author harrylee (harryleexyz(a)qq.com)
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class RememberMeUtils {

    private static String digestHex16(String credentialEncryptSalt, String data) {
        MD5 md5 = new MD5(credentialEncryptSalt.getBytes(StandardCharsets.UTF_8));
        return md5.digestHex16(data);
    }

    /**
     * Credential encryption algorithm: MD5 encryption
     *
     * @param request      request
     * @param simpleConfig simpleConfig
     * @return boolean
     */
    public static boolean enableRememberMe(JapHttpRequest request, SimpleConfig simpleConfig) {
        return BooleanUtil.toBoolean(request.getParameter(simpleConfig.getRememberMeField()));
    }

    /**
     * Encrypted acquisition instance.
     *
     * @param simpleConfig config
     * @param username     username
     * @return RememberMeDetails
     */
    public static RememberMeDetails encode(SimpleConfig simpleConfig, String username) {
        long expiryTime = System.currentTimeMillis() + simpleConfig.getRememberMeCookieExpire();
        // username:tokenExpiryTime
        String md5Data = username + DEFAULT_DELIMITER + expiryTime;
        String md5Key = digestHex16(simpleConfig.getCredentialEncryptSalt(), md5Data);
        // username:tokenExpiryTime:key
        String base64Data = md5Data + DEFAULT_DELIMITER + md5Key;
        return new RememberMeDetails()
            .setUsername(username)
            .setExpiryTime(expiryTime)
            .setEncodeValue(Base64.encode(base64Data));
    }

    /**
     * Decryption acquisition instance.
     *
     * @param simpleConfig config
     * @param cookieValue  cookie value
     * @return RememberMeDetails
     */
    public static RememberMeDetails decode(SimpleConfig simpleConfig, String cookieValue) throws JapException {
        String base64DecodeValue;
        try {
            base64DecodeValue = Base64.decodeStr(cookieValue);
        } catch (RuntimeException e) {
            throw new JapException(JapErrorCode.INVALID_MEMBERME_COOKIE);
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
            String md5Key = digestHex16(simpleConfig.getCredentialEncryptSalt(), md5Data);
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
