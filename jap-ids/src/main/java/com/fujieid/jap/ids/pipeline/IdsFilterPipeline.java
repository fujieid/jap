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

/**
 * For the filter of jap-ids, a pipeline interface is provided. When an exception occurs in the filter,
 * <p>
 * you can use {@link com.fujieid.jap.ids.pipeline.IdsFilterPipeline#errorHandle(JapHttpRequest, JapHttpResponse, Throwable)} to handle the exception information .
 * <p>
 * The data in json format is returned by default:
 *
 * <code>
 * {
 * "error": "error",
 * "error_description": "error_description"
 * }
 * </code>
 * <p>
 * Note: Only need to implement the {@link com.fujieid.jap.ids.pipeline.IdsFilterPipeline#errorHandle(JapHttpRequest, JapHttpResponse, Throwable)} method of this interface.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.2
 */
public interface IdsFilterPipeline extends IdsPipeline<Object> {
    /**
     * Callback when the program is abnormal
     *
     * @param request   current HTTP request
     * @param response  current HTTP response
     * @param throwable any exception thrown on handler execution, if any.
     */
    @Override
    default void errorHandle(JapHttpRequest request, JapHttpResponse response, Throwable throwable) {
        IdsPipeline.super.errorHandle(request, response, throwable);
    }

    /**
     * Operations before business process processing, such as initializing resources, etc.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return boolean
     */
    @Deprecated
    @Override
    default boolean preHandle(JapHttpRequest request, JapHttpResponse response) {
        return IdsPipeline.super.preHandle(request, response);
    }

    /**
     * Intercept the execution of a handler
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return Object
     */
    @Deprecated
    @Override
    default Object postHandle(JapHttpRequest request, JapHttpResponse response) {
        return IdsPipeline.super.postHandle(request, response);
    }

    /**
     * Callback after business process processing is completed, such as recycling resources, recording status, etc.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    @Deprecated
    @Override
    default void afterHandle(JapHttpRequest request, JapHttpResponse response) {
        IdsPipeline.super.afterHandle(request, response);
    }
}
