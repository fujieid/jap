/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL LICENSE 3.0;
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
package com.fujieid.jap.http;

/**
 * Define an interface to adapt to session objects of different frameworks,
 * such as: <code>javax.servlet.http.HttpSession</code> or <code>com.blade.mvc.http.Session</code>
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
public interface JapHttpSession {

    /**
     * Returns the time when this session was created, measured in milliseconds since midnight January 1, 1970 GMT.
     *
     * @return a <code>long</code> specifying when this session was created, expressed in milliseconds since 1/1/1970
     * GMT
     */
    long getCreationTime();

    /**
     * Returns a string containing the unique identifier assigned to this session. The identifier is assigned by the
     * servlet container and is implementation dependent.
     *
     * @return a string specifying the identifier assigned to this session
     */
    String getId();

    /**
     * Returns the object bound with the specified name in this session, or <code>null</code> if no object is bound
     * under the name.
     *
     * @param name a string specifying the name of the object
     * @return the object with the specified name
     */
    Object getAttribute(String name);

    /**
     * Binds an object to this session, using the name specified. If an object of the same name is already bound to the
     * session, the object is replaced.
     *
     * @param name  the name to which the object is bound; cannot be null
     * @param value the object to be bound; cannot be null
     */
    void setAttribute(String name, Object value);

    /**
     * Removes the object bound with the specified name from this session. If the session does not have an object bound
     * with the specified name, this method does nothing.
     *
     * @param name the name of the object to remove from this session
     */
    void removeAttribute(String name);

    /**
     * Invalidates this session then unbinds any objects bound to it.
     */
    void invalidate();
}
