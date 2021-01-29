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
package com.fujieid.jap.social;

import com.fujieid.jap.core.AuthenticateConfig;

/**
 * Configuration file of third-party authorization login module
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class SocialConfig extends AuthenticateConfig {

    /**
     * The name of a third-party platform regardless of case. For example: gitee、github、google
     */
    private String platform;

    /**
     * An opaque value used by the client to maintain state between the request and callback.  The authorization
     * server includes this value when redirecting the user-agent back to the client
     *
     * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1.1" target="_blank">https://tools.ietf.org/html/rfc6749#section-4.1.1</a>
     */
    private String state;

    /**
     * Package of {@code AuthRequest} implementation classes
     */
    private String[] scanPackages;

    /**
     * Exclude classes that do not need to be registered, such as: {@code AuthDefaultRequest}、
     * {@code AbstractAuthWeChatEnterpriseRequest}、{@code AuthRequest}
     */
    private String[] exclusionClassNames;

    public String getPlatform() {
        return platform;
    }

    public SocialConfig setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getState() {
        return state;
    }

    public SocialConfig setState(String state) {
        this.state = state;
        return this;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public SocialConfig setScanPackages(String[] scanPackages) {
        this.scanPackages = scanPackages;
        return this;
    }

    public String[] getExclusionClassNames() {
        return exclusionClassNames;
    }

    public SocialConfig setExclusionClassNames(String[] exclusionClassNames) {
        this.exclusionClassNames = exclusionClassNames;
        return this;
    }
}
