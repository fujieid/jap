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
package com.fujieid.jap.ids.pipeline;

import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.ids.model.UserInfo;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class CustomizeSignInPipeline implements IdsSignInPipeline {
    /**
     * Callback when the program is abnormal
     *
     * @param request   current HTTP request
     * @param response  current HTTP response
     * @param throwable any exception thrown on handler execution, if any.
     */
    @Override
    public void errorHandle(JapHttpRequest request, JapHttpResponse response, Throwable throwable) {
        System.out.println("CustomizeSignInPipeline >> errorHandle");
    }

    /**
     * Operations before business process processing, such as initializing resources, etc.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return boolean
     */
    @Override
    public boolean preHandle(JapHttpRequest request, JapHttpResponse response) {
        System.out.println("CustomizeSignInPipeline >> preHandle");
        return true;
    }

    /**
     * Intercept the execution of a handler
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return UserInfo
     */
    @Override
    public UserInfo postHandle(JapHttpRequest request, JapHttpResponse response) {
        System.out.println("CustomizeSignInPipeline >> postHandle");
        return null;
    }

    /**
     * Callback after business process processing is completed, such as recycling resources, recording status, etc.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    @Override
    public void afterHandle(JapHttpRequest request, JapHttpResponse response) {
        System.out.println("CustomizeSignInPipeline >> afterHandle");
    }
}
