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
package com.fujieid.jap.simple;

/**
 * @author harrylee (harryleexyz(a)qq.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class RememberMeDetails {

    /**
     * username
     */
    private String username;

    /**
     * expiry time
     */
    private long expiryTime;
    /**
     * encode value
     */
    private String encodeValue;

    public String getUsername() {
        return username;
    }

    public RememberMeDetails setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public RememberMeDetails setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }

    public String getEncodeValue() {
        return encodeValue;
    }

    public RememberMeDetails setEncodeValue(String encodeValue) {
        this.encodeValue = encodeValue;
        return this;
    }
}
