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

import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.IdsResponse;
import com.xkcoding.json.JsonUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The pipeline is an enhanced interface for the business process of {@code JustAuthPlus}<br>
 * <p>
 * The workflow interface allows custom processing program execution chains, <br>
 * and based on pipeline, common preprocessing logic can be added to some processing programs.<br>
 * <p>
 * The pipeline interface can be implemented to enhance the business process of {@code JustAuthPlus}.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.2
 */
public interface IdsPipeline<T> {

    /**
     * Callback when the program is abnormal
     *
     * @param servletRequest  current HTTP request
     * @param servletResponse current HTTP response
     * @param throwable       any exception thrown on handler execution, if any.
     */
    default void errorHandle(ServletRequest servletRequest, ServletResponse servletResponse, Throwable throwable) {
        IdsResponse<String, Object> response = new IdsResponse<>();
        if (throwable instanceof IdsException) {
            IdsException idsException = (IdsException) throwable;
            response.error(idsException.getError())
                .errorDescription(idsException.getErrorDescription());
        } else {
            response.errorDescription(throwable.getMessage());
        }
        String errorResponseStr = JsonUtil.toJsonString(response);
        servletResponse.setContentType("text/html;charset=UTF-8");
        servletResponse.setContentLength(errorResponseStr.getBytes(StandardCharsets.UTF_8).length);
        try {
            servletResponse.getWriter().write(errorResponseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operations before business process processing, such as initializing resources, etc.
     *
     * @param servletRequest  current HTTP request
     * @param servletResponse current HTTP response
     * @return boolean
     */
    default boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) {
        return true;
    }

    /**
     * Intercept the execution of a handler
     *
     * @param servletRequest  current HTTP request
     * @param servletResponse current HTTP response
     * @return <T>
     */
    default T postHandle(ServletRequest servletRequest, ServletResponse servletResponse) {
        return null;
    }

    /**
     * Callback after business process processing is completed, such as recycling resources, recording status, etc.
     *
     * @param servletRequest  current HTTP request
     * @param servletResponse current HTTP response
     */
    default void afterHandle(ServletRequest servletRequest, ServletResponse servletResponse) {
    }
}
