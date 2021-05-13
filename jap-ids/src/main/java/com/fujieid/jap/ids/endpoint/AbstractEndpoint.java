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
package com.fujieid.jap.ids.endpoint;

import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.pipeline.IdsPipeline;
import com.fujieid.jap.ids.service.Oauth2Service;
import com.fujieid.jap.ids.service.Oauth2ServiceImpl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Abstract classes common to various endpoints
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractEndpoint {
    protected final Oauth2Service oauth2Service;

    public AbstractEndpoint() {
        this.oauth2Service = new Oauth2ServiceImpl();
    }

    protected IdsPipeline<UserInfo> getUserInfoIdsPipeline(IdsPipeline<UserInfo> idsSigninPipeline) {
        if (null == idsSigninPipeline) {
            idsSigninPipeline = new IdsPipeline<UserInfo>() {
                @Override
                public boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) {
                    return IdsPipeline.super.preHandle(servletRequest, servletResponse);
                }

                @Override
                public UserInfo postHandle(ServletRequest servletRequest, ServletResponse servletResponse) {
                    return IdsPipeline.super.postHandle(servletRequest, servletResponse);
                }
            };
        }
        return idsSigninPipeline;
    }
}
