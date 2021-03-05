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
package com.fujieid.jap.core.result;

import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;

/**
 * jap unified response class, standardized data return format.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapResponse implements Serializable {
    private int code;
    private String message;
    private Object data;

    public static JapResponse success() {
        return new JapResponse()
            .setCode(JapErrorCode.SUCCESS.getErrroCode())
            .setMessage(JapErrorCode.SUCCESS.getErrorMessage());
    }

    public static JapResponse success(Object data) {
        return new JapResponse()
            .setCode(200)
            .setData(data);
    }

    public static JapResponse error(JapErrorCode errorCode) {
        return new JapResponse()
            .setCode(errorCode.getErrroCode())
            .setMessage(errorCode.getErrorMessage());
    }

    public static JapResponse error(int errorCode, String errorMessage) {
        return new JapResponse()
            .setCode(errorCode)
            .setMessage(errorMessage);
    }

    /**
     * Whether the result currently returned is successful
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return this.getCode() == 200;
    }

    /**
     * Methods provided for social, oauth, and oidc login strategy. The business flow is as follows:
     * <p>
     * 1. When not logged in, call the <code>strategy.authenticate(x)</code> method,
     * and the returned <code>JapResponse.data</code> is the authorize url of the third-party platform
     * <p>
     * 2. When the third-party login completes the callback, call the <code>strategy.authenticate(x)</code> method,
     * and the returned <code>JapResponse.data</code> is jap user
     * <p>
     * After calling the <code>strategy.authenticate(x)</code> method,
     * the developer can use <code>japResponse.isRedirectUrl()</code> to determine whether the current processing result
     * is the authorize url that needs to be redirected, or the jap user after successful login
     * <p>
     *
     * @return boolean
     */
    public boolean isRedirectUrl() {
        Object data = this.getData();
        return isSuccess()
            && ObjectUtil.isNotNull(data)
            && data instanceof String
            && ((String) data).startsWith("http");
    }

    public int getCode() {
        return code;
    }

    public JapResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JapResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JapResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
