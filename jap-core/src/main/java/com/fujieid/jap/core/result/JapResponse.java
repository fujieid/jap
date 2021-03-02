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

    public boolean isSuccess() {
        return this.getCode() == 200;
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
