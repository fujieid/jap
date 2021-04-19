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
package com.fujieid.jap.ids.exception;

import com.fujieid.jap.ids.model.enums.ErrorResponse;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.1
 */
public class IdsException extends RuntimeException {

    private String error;
    private String errorDescription;

    public IdsException(String message) {
        super(message);
        this.errorDescription = message;
    }

    public IdsException(String error, String errorDescription) {
        super(error + ": " + errorDescription);
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public IdsException(ErrorResponse errorResponse) {
        this.error = errorResponse.getError();
        this.errorDescription = errorResponse.getErrorDescription();
    }

    public String getError() {
        return error;
    }

    public IdsException setError(String error) {
        this.error = error;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public IdsException setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }
}
