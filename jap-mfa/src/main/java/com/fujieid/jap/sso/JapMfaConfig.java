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
package com.fujieid.jap.sso;

import java.io.File;

/**
 * Multi-Factor Authenticator Configuration
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapMfaConfig {
    /**
     * the number of digits in the generated code.
     */
    private int digits = 6;
    /**
     * the time step size, in milliseconds, as specified by RFC 6238. The default value is 30.000s.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6238#section-5.2" target="_blank">5.2.  Validation and Time-Step Size</a>
     */
    private long period = 30000;
    /**
     * the crypto algorithm (HMACSHA1, HMACSHA256, HMACSHA512)
     */
    private JapMfaAlgorithm algorithm = JapMfaAlgorithm.HMACSHA1;

    private String qrcodeTempPath = System.getProperties().getProperty("user.home") + File.separator + "jap" + File.separator + "temp";

    private int qrcodeWidth = 200;

    private int qrcodeHeight = 200;

    private String qrcodeImgType = "gif";

    public int getDigits() {
        return digits;
    }

    public JapMfaConfig setDigits(int digits) {
        this.digits = digits;
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public JapMfaConfig setPeriod(long period) {
        this.period = period;
        return this;
    }

    public JapMfaAlgorithm getAlgorithm() {
        return algorithm;
    }

    public JapMfaConfig setAlgorithm(JapMfaAlgorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public String getQrcodeTempPath() {
        return qrcodeTempPath;
    }

    public JapMfaConfig setQrcodeTempPath(String qrcodeTempPath) {
        this.qrcodeTempPath = qrcodeTempPath;
        return this;
    }

    public int getQrcodeWidth() {
        return qrcodeWidth;
    }

    public JapMfaConfig setQrcodeWidth(int qrcodeWidth) {
        this.qrcodeWidth = qrcodeWidth;
        return this;
    }

    public int getQrcodeHeight() {
        return qrcodeHeight;
    }

    public JapMfaConfig setQrcodeHeight(int qrcodeHeight) {
        this.qrcodeHeight = qrcodeHeight;
        return this;
    }

    public String getQrcodeImgType() {
        return qrcodeImgType;
    }

    public JapMfaConfig setQrcodeImgType(String qrcodeImgType) {
        this.qrcodeImgType = qrcodeImgType;
        return this;
    }
}
