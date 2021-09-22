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
package com.fujieid.jap.core.strategy;

import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;

/**
 * The unified implementation interface of JAP Strategy, which must be implemented for all specific business policies.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JapStrategy {

    /**
     * This function must be overridden by subclasses.  In abstract form, it always throws an exception.
     *
     * @param config   Authenticate Config
     * @param request  The request to authenticate
     * @param response The response to authenticate
     * @return JapResponse
     */
    default JapResponse authenticate(AuthenticateConfig config, JapHttpRequest request, JapHttpResponse response) {
        return JapResponse.error(JapErrorCode.ERROR.getErrroCode(), "JapStrategy#authenticate(AuthenticateConfig, HttpServletRequest, HttpServletResponse) must be overridden by subclass");
    }
}
